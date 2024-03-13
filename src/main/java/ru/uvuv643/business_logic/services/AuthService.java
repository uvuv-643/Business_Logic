package ru.uvuv643.business_logic.services;

import org.springframework.stereotype.Service;
import ru.uvuv643.business_logic.enums.RoleEnum;
import ru.uvuv643.business_logic.models.Role;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Optional<User> getUserByTokenBearer(String authToken) {
        String[] tokenParts = authToken.split(" ");
        if (tokenParts.length == 2 && tokenParts[0].equals("Bearer")) {
            return userRepository.findByToken(tokenParts[1]);
        }
        return Optional.empty();
    }

    private Optional<User> getUserByToken(String authToken) {
        return userRepository.findByToken(authToken);
    }

    private boolean checkRole(RoleEnum roleEnum, String authToken) {
        Optional<User> user = this.getUserByToken(authToken);
        return user.map(value -> value.getRoles().stream().map(Role::getId).toList().contains(Role.roles.get(roleEnum))).orElse(false);
    }

    public boolean isAdmin(String authToken) {
        return this.checkRole(RoleEnum.ADMIN, authToken);
    }

    public boolean isUser(String authToken) {
        return this.checkRole(RoleEnum.USER, authToken);
    }

    public Optional<User> getUser(String authToken) {
        return this.getUserByToken(authToken);
    }

}
