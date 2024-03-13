package ru.uvuv643.business_logic.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.uvuv643.business_logic.enums.ModerationStatusEnum;
import ru.uvuv643.business_logic.http.requests.ArticleRequest;
import ru.uvuv643.business_logic.http.requests.GetArticlesRequest;
import ru.uvuv643.business_logic.http.responses.ArticleResponse;
import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.ArticleAttachment;
import ru.uvuv643.business_logic.models.ModerationStatus;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.repositories.ArticleRepository;
import ru.uvuv643.business_logic.repositories.AttachmentRepository;
import ru.uvuv643.business_logic.repositories.ModerationStatusRepository;
import ru.uvuv643.business_logic.services.AuthService;

import java.util.*;

@Controller
public class ArticleController {

    public static final int MINIMUM_NUMBER_OF_ATTACHMENTS = 2;
    private final ArticleRepository articleRepository;
    private final AttachmentRepository attachmentRepository;
    private final ModerationStatusRepository statusRepository;
    private final AuthService authService;

    public ArticleController(
            ArticleRepository articleRepository,
            AttachmentRepository attachmentRepository,
            ModerationStatusRepository statusRepository,
            AuthService authService
    ) {
        this.articleRepository = articleRepository;
        this.attachmentRepository = attachmentRepository;
        this.statusRepository = statusRepository;
        this.authService = authService;
    }

    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    @ResponseBody
    public Object storeArticle(@RequestBody @Valid ArticleRequest request, @CookieValue(value = "access-token", required = false) String authToken) {
        if (authService.isUser(authToken)) {
            Optional<User> userOptional = authService.getUser(authToken);
            if (userOptional.isPresent()) {
                if (request.getAttachmentIds().size() < MINIMUM_NUMBER_OF_ATTACHMENTS) {
                    return new ResponseEntity<>("Должно быть прикреплено минимум " + MINIMUM_NUMBER_OF_ATTACHMENTS + " скриншотов", HttpStatus.BAD_REQUEST);
                }
                User user = userOptional.get();
                Article createdArticle = new Article();
                createdArticle.setTitle(request.getTitle());
                createdArticle.setContent(request.getContent());
                createdArticle.setFileLink(request.getFileLink());
                createdArticle.setVersion(request.getVersion());
                createdArticle.setUser(user);
                Optional<ModerationStatus> targetStatus = statusRepository.findById(ModerationStatus.statuses.get(ModerationStatusEnum.ACTIVE));
                targetStatus.ifPresent(createdArticle::setStatus);
                List<ArticleAttachment> relatedAttachments = (List<ArticleAttachment>) attachmentRepository.findAllById(request.getAttachmentIds());
                if (relatedAttachments.size() != request.getAttachmentIds().size()) {
                    return new ResponseEntity<>("Некорректные ID вложений.", HttpStatus.BAD_REQUEST);
                }
                for (ArticleAttachment attachment : relatedAttachments) {
                    if (!Objects.equals(attachment.getUser().getId(), user.getId())) {
                        return new ResponseEntity<>("Вы прикрепили не своё вложение. ID: " + attachment.getId(), HttpStatus.BAD_REQUEST);
                    }
                }
                createdArticle.setArticleAttachments(relatedAttachments);
                articleRepository.save(createdArticle);
                return new ArticleResponse(createdArticle);
            }
        }
        return new ResponseEntity<>("У вас нет прав на создание статьи", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    @ResponseBody
    public Object allArticles(GetArticlesRequest request, @CookieValue(value = "access-token", required = false) String authToken) {
        if (authService.isAdmin(authToken)) {
            if (request.getModerationStatusId() != null) {
                List<Article> articles = articleRepository.findByStatusId(request.getModerationStatusId());
                return articles.stream().map(ArticleResponse::new);
            }
            List<Article> articles = (List<Article>) articleRepository.findAll();
            return articles.stream().map(ArticleResponse::new);
        } else if (authService.isUser(authToken)) {
            List<Article> articles = articleRepository.findByStatusId(ModerationStatus.statuses.get(ModerationStatusEnum.ACCEPTED));
            return articles.stream().map(ArticleResponse::new);
        }
        return new ResponseEntity<>("У вас нет прав на просмотр статей", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/articles/my", method = RequestMethod.GET)
    @ResponseBody
    public Object myArticles(@CookieValue(value = "access-token", required = false) String authToken) {
        if (authService.isUser(authToken)) {
            Optional<User> userOptional = authService.getUser(authToken);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Article> articles = articleRepository.findByUserId(user.getId());
                return articles.stream().map(ArticleResponse::new);
            }
        }
        return new ResponseEntity<>("У вас нет прав на просмотр статей", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateArticle(@PathVariable(value = "id") Long id, @RequestBody ArticleRequest request, @CookieValue(value = "access-token", required = false) String authToken) {
        Optional<Article> targetArticle = articleRepository.findById(id);
        if (targetArticle.isEmpty()) {
            return new ResponseEntity<>("Не найдено такой статьи", HttpStatus.NOT_FOUND);
        }
        Article article = targetArticle.get();
        boolean updated = false;
        if (authService.isUser(authToken)) {
            Optional<User> userOptional = authService.getUser(authToken);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!Objects.equals(article.getUser().getId(), user.getId())) {
                    return new ResponseEntity<>("Это не ваша статья!!!", HttpStatus.FORBIDDEN);
                }
                if (request.getContent() != null) article.setContent(request.getContent());
                if (request.getTitle() != null) article.setTitle(request.getTitle());
                if (request.getVersion() != null) article.setVersion(request.getVersion());
                if (request.getFileLink() != null) article.setFileLink(request.getFileLink());

                if (request.getAttachmentIds() != null) {
                    if (request.getAttachmentIds().size() < MINIMUM_NUMBER_OF_ATTACHMENTS) {
                        return new ResponseEntity<>("Должно быть прикреплено минимум " + MINIMUM_NUMBER_OF_ATTACHMENTS + " скриншотов", HttpStatus.BAD_REQUEST);
                    }
                    List<ArticleAttachment> relatedAttachments = (List<ArticleAttachment>) attachmentRepository.findAllById(request.getAttachmentIds());
                    if (relatedAttachments.size() != request.getAttachmentIds().size()) {
                        return new ResponseEntity<>("Некорректные ID вложений.", HttpStatus.BAD_REQUEST);
                    }
                    for (ArticleAttachment attachment : relatedAttachments) {
                        if (!Objects.equals(attachment.getUser().getId(), user.getId())) {
                            return new ResponseEntity<>("Вы прикрепили не своё вложение. ID: " + attachment.getId(), HttpStatus.BAD_REQUEST);
                        }
                    }
                    article.setArticleAttachments(relatedAttachments);
                }
                updated = true;
            }
        }
        if (authService.isAdmin(authToken)) {
            if (request.getStatusId() != null) {
                Optional<ModerationStatus> status = statusRepository.findById(request.getStatusId());
                if (status.isEmpty()) {
                    return new ResponseEntity<>("Нет такого статуса", HttpStatus.BAD_REQUEST);
                }
                article.setStatus(status.get());
            }
            updated = true;
        }
        if (updated) {
            articleRepository.save(article);
            return new ArticleResponse(article);
        }
        return new ResponseEntity<>("У вас нет прав на просмотр статей", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteArticle(@PathVariable(value = "id") Long id, @CookieValue(value = "access-token", required = false) String authToken) {
        Optional<Article> targetArticle = articleRepository.findById(id);
        if (targetArticle.isEmpty()) {
            return new ResponseEntity<>("Не найдено такой статьи", HttpStatus.NOT_FOUND);
        }
        Article article = targetArticle.get();
        if (authService.isAdmin(authToken)) {
            articleRepository.delete(article);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
        return new ResponseEntity<>("У вас нет прав для выполнения этого действия", HttpStatus.FORBIDDEN);
    }

}