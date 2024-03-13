package ru.uvuv643.business_logic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uvuv643.business_logic.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?1";
    public static final String FIND_USER_BY_TOKEN = "SELECT * FROM users WHERE token = ?1";

    @Query(value = FIND_USER_BY_EMAIL, nativeQuery = true)
    public Optional<User> findByEmail(String email);

    @Query(value = FIND_USER_BY_TOKEN, nativeQuery = true)
    public Optional<User> findByToken(String token);

}
