package ru.uvuv643.business_logic.http.responses;

import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.ModerationStatus;
import ru.uvuv643.business_logic.models.User;

import java.util.List;
import java.util.stream.Collectors;


public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private String fileLink;
    private String version;
    private String createdAt;
    private ModerationStatus status;
    private User user;
    private List<ArticleAttachmentResponse> attachments;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.fileLink = article.getFileLink();
        this.version = article.getVersion();
        this.status = article.getStatus();
        this.createdAt = article.getCreatedAt().toLocalDateTime().toString();
        this.attachments = article.getArticleAttachments().stream().map(ArticleAttachmentResponse::new).collect(Collectors.toList());
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

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<ArticleAttachmentResponse> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ArticleAttachmentResponse> attachments) {
        this.attachments = attachments;
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
