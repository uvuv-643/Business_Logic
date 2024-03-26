package ru.uvuv643.business_logic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.uvuv643.business_logic.http.responses.ArticleResponse;
import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.repositories.ArticleRepository;
import ru.uvuv643.business_logic.repositories.ArticleUnlockedRepository;
import ru.uvuv643.business_logic.services.ArticleGuardService;
import ru.uvuv643.business_logic.services.AuthService;

import java.util.Optional;

@Controller
public class PurchaseController {

    private final ArticleGuardService articleGuardService;

    private final ArticleRepository articleRepository;

    private final AuthService authService;

    private final ArticleUnlockedRepository articleUnlockedRepository;

    public PurchaseController(ArticleGuardService articleGuardService, ArticleRepository articleRepository, AuthService authService, ArticleUnlockedRepository articleUnlockedRepository) {
        this.articleGuardService = articleGuardService;
        this.articleRepository = articleRepository;
        this.authService = authService;
        this.articleUnlockedRepository = articleUnlockedRepository;
    }

    @RequestMapping(path = "/unlock/{id}")
    @ResponseBody
    public Object buy(@PathVariable(value = "id") Long id, @CookieValue(value = "access-token", required = false) String authToken) {
        Optional<Article> currentArticle = articleRepository.findById(id);
        if (currentArticle.isPresent()) {
            if (authService.isUser(authToken)) {
                Optional<User> currentUser = authService.getUser(authToken);
                if (currentUser.isPresent()) {
                    boolean isAvailableToReadFull = articleGuardService.canViewArticle(currentUser.get(), currentArticle.get());
                    if (!isAvailableToReadFull) {
                        boolean purchased = articleGuardService.unlockArticleAndAcceptCoins(currentUser.get(), currentArticle.get());
                        if (purchased) {
                            return new ArticleResponse(currentArticle.get());
                        } else {
                            return new ResponseEntity<>("Не хватает коинов!", HttpStatus.FORBIDDEN);
                        }
                    } else {
                        return new ArticleResponse(currentArticle.get());
                    }
                }
            }
        }
        return new ResponseEntity<>("Нет такой статьи", HttpStatus.NOT_FOUND);
    }

}
