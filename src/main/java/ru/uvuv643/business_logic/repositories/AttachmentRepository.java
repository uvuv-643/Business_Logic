package ru.uvuv643.business_logic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uvuv643.business_logic.models.Article;
import ru.uvuv643.business_logic.models.ArticleAttachment;
import ru.uvuv643.business_logic.models.User;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends CrudRepository<ArticleAttachment, Long> {

}
