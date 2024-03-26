package ru.uvuv643.business_logic.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.ArticlesUnlocked;
import ru.uvuv643.business_logic.models.Subscription;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.policies.UserPolicy;
import ru.uvuv643.business_logic.repositories.ArticleUnlockedRepository;

import java.util.List;

@Service
public class ArticleGuardService {

    @Autowired
    private UserPolicy userPolicy;

    @Autowired
    private ArticleUnlockedRepository articleUnlockedRepository;

    @Transactional
    protected void unlockArticle(User user, Article article) {
        ArticlesUnlocked unlockedArticle = new ArticlesUnlocked();
        unlockedArticle.setArticle(article);
        unlockedArticle.setUser(user);
        articleUnlockedRepository.save(unlockedArticle);
    }

    @Transactional
    public boolean unlockArticleAndAcceptCoins(User user, Article article) throws RuntimeException {
        if (this.userPolicy.canBuy(user, article)) {
            List<Subscription> userSubscriptions = user.getSubscriptions();
            double coinsLeftToPay = article.getPrice();
            for (Subscription subscription : userSubscriptions) {
                if (coinsLeftToPay <= 0.0001) {
                    unlockArticle(user, article);
                    return true;
                }
                double currentSubscriptionCountCoins = subscription.getCoinsLeft();
                if (currentSubscriptionCountCoins > 0) {
                    double coinsInSubscriptionLeft = Math.max(0.0, currentSubscriptionCountCoins - coinsLeftToPay);
                    coinsLeftToPay -= subscription.getCoinsLeft() - coinsInSubscriptionLeft;
                    subscription.setCoinsLeft(coinsInSubscriptionLeft);
                }
            }
            if (coinsLeftToPay > 0.0001) {
                throw new RuntimeException("iитегиаl seгveг еггог");
            }
            unlockArticle(user, article);
            return true;
        }
        return false;
    }

    public boolean canViewArticle(User user, Article article) {
        return this.userPolicy.canView(user, article);
    }

}
