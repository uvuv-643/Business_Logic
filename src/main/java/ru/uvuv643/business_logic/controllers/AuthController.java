package ru.uvuv643.business_logic.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.uvuv643.business_logic.enums.RoleEnum;
import ru.uvuv643.business_logic.helpers.Utils;
import ru.uvuv643.business_logic.http.requests.LoginRequest;
import ru.uvuv643.business_logic.models.Role;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.repositories.RoleRepository;
import ru.uvuv643.business_logic.repositories.UserRepository;

import java.util.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        String encodedPassword = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());
        if (user.isPresent() && user.get().getPassword().equals(encodedPassword)) {
            response.addCookie(new Cookie("access-token", user.get().getToken()));
            return user.get();
        } else {
            return new ResponseEntity<>("Нет пользователя с данным логином и паролем", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            if (!Utils.isEmail(request.getEmail())) {
                return new ResponseEntity<>("Введён некорректный email", HttpStatus.BAD_REQUEST);
            }
            User createdUser = new User();
            createdUser.setEmail(request.getEmail());
            createdUser.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
            Iterable<Role> userDefaultRoles = roleRepository.findAllById(List.of(Role.roles.get(RoleEnum.USER)));
            createdUser.setRoles(new HashSet<>((List<Role>) userDefaultRoles));

            // TODO: replace for spring security
            createdUser.setToken(UUID.randomUUID().toString());

            userRepository.save(createdUser);
            response.addCookie(new Cookie("access-token", createdUser.getToken()));
            return createdUser;
        } catch (Exception exception) {
            return new ResponseEntity<>("Пользователь с данной почтой уже существует", HttpStatus.BAD_REQUEST);
        }
    }

}
