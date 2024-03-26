package ru.uvuv643.business_logic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.ArticlesUnlocked;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleUnlockedRepository extends CrudRepository<ArticlesUnlocked, Long> {

    public static final String FIND_BY_USER_AND_ARTICLE = "SELECT * FROM articles_unlocked WHERE user_id = ?1 AND article_id = ?2";

    @Query(value = FIND_BY_USER_AND_ARTICLE, nativeQuery = true)
    public Optional<ArticlesUnlocked> findByUserAndArticle(Long userId, Long articleId);

}
