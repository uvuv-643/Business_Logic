package ru.uvuv643.business_logic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uvuv643.business_logic.models.ModerationStatus;
import ru.uvuv643.business_logic.models.Role;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.models.general.AbstractSeeder;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
