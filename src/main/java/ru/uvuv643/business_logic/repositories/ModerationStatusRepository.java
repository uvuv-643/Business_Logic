package ru.uvuv643.business_logic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uvuv643.business_logic.models.ArticleAttachment;
import ru.uvuv643.business_logic.models.ModerationStatus;

@Repository
public interface ModerationStatusRepository extends CrudRepository<ModerationStatus, Long> {

}
