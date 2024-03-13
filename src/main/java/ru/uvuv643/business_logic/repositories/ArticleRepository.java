package ru.uvuv643.business_logic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uvuv643.business_logic.models.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    public static final String FIND_ARTICLES_BY_USER_ID = "SELECT * FROM articles WHERE user_id = ?1";
    public static final String FIND_ARTICLES_BY_STATUS_ID = "SELECT * FROM articles WHERE status_id = ?1";

    @Query(value = FIND_ARTICLES_BY_USER_ID, nativeQuery = true)
    public List<Article> findByUserId(Long userId);

    @Query(value = FIND_ARTICLES_BY_STATUS_ID, nativeQuery = true)
    public List<Article> findByStatusId(Long statusId);

}
