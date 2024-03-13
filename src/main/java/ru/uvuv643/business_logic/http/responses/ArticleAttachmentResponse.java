package ru.uvuv643.business_logic.http.responses;

import ru.uvuv643.business_logic.models.ArticleAttachment;

public class ArticleAttachmentResponse {

    private Long id;
    private String path;

    public ArticleAttachmentResponse(ArticleAttachment attachment) {
        this.id = attachment.getId();
        this.path = attachment.getPath();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
