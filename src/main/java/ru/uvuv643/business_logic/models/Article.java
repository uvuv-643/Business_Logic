package ru.uvuv643.business_logic.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String fileLink;

    @Column
    private String version;

    @Column
    private double price;

    @Column
    @CurrentTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ModerationStatus status;

    @OneToMany
    @JoinColumn(name = "article_id")
    private List<ArticleAttachment> articleAttachments;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<ArticleAttachment> getArticleAttachments() {
        return articleAttachments;
    }

    public void setArticleAttachments(List<ArticleAttachment> articleAttachments) {
        this.articleAttachments = articleAttachments;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", fileLink='" + fileLink + '\'' +
                ", version='" + version + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", status=" + status +
                ", articleAttachments=" + articleAttachments +
                '}';
    }
}
