package ru.uvuv643.business_logic.http.responses;

import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.ModerationStatus;
import ru.uvuv643.business_logic.models.User;

public class ArticlePreviewResponse {

    private Long id;
    private String title;
    private String content;
    private ModerationStatus status;
    private User user;

    public ArticlePreviewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.status = article.getStatus();
        this.user = article.getUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ModerationStatus getStatus() {
        return status;
    }

    public void setStatus(ModerationStatus status) {
        this.status = status;
    }
}
