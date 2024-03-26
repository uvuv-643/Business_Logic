package ru.uvuv643.business_logic.policies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.uvuv643.business_logic.enums.RoleEnum;
import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.Role;
import ru.uvuv643.business_logic.models.Subscription;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.repositories.ArticleUnlockedRepository;

import java.util.Objects;

@Service
public class UserPolicy {

    final
    ArticleUnlockedRepository articleUnlockedRepository;

    public UserPolicy(ArticleUnlockedRepository articleUnlockedRepository) {
        this.articleUnlockedRepository = articleUnlockedRepository;
    }

    public boolean canView(User user, Article article) {
        System.out.println(user.getTotalCoins());
        if (user.getRoles().stream().map(Role::getId).toList().contains(Role.roles.get(RoleEnum.ADMIN))) {
            return true;
        }
        return this.articleUnlockedRepository.findByUserAndArticle(user.getId(), article.getId()).isPresent() || user.getId().equals(article.getUser().getId());
    }

    public boolean canBuy(User user, Article article) {
        return user.getTotalCoins() >= article.getPrice();
    }

}
