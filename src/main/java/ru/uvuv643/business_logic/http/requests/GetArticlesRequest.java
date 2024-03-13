package ru.uvuv643.business_logic.http.requests;


public class GetArticlesRequest {

    private Long moderationStatusId;

    public Long getModerationStatusId() {
        return moderationStatusId;
    }

    public void setModerationStatusId(Long moderationStatusId) {
        this.moderationStatusId = moderationStatusId;
    }
}
