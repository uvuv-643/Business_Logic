package ru.uvuv643.business_logic.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.uvuv643.business_logic.http.requests.AttachmentRequest;
import ru.uvuv643.business_logic.http.responses.ArticleAttachmentResponse;
import ru.uvuv643.business_logic.models.ArticleAttachment;
import ru.uvuv643.business_logic.models.User;
import ru.uvuv643.business_logic.repositories.AttachmentRepository;
import ru.uvuv643.business_logic.services.AuthService;
import ru.uvuv643.business_logic.services.StorageService;

import java.util.Optional;


@Controller
public class AttachmentController {

    private final AttachmentRepository attachmentRepository;
    private final AuthService authService;
    private final StorageService storageService;

    public AttachmentController(
            AttachmentRepository attachmentRepository,
            AuthService authService,
            StorageService storageService
    ) {
        this.attachmentRepository = attachmentRepository;
        this.authService = authService;
        this.storageService = storageService;
    }

    @RequestMapping(value = "/attachments", method = RequestMethod.POST)
    @ResponseBody
    public Object storeAttachment(@RequestBody @Valid AttachmentRequest request, @CookieValue(value = "access-token", required = false) String authToken) {
        if (authService.isUser(authToken)) {
            Optional<User> userOptional = authService.getUser(authToken);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                ArticleAttachment createdAttachment = new ArticleAttachment();
                createdAttachment.setUser(user);
                Optional<String> storedAttachment = storageService.storeBase64Image(request.getBase64Image());
                if (storedAttachment.isPresent()) {
                    createdAttachment.setPath(storedAttachment.get());
                    attachmentRepository.save(createdAttachment);
                    return new ArticleAttachmentResponse(createdAttachment);
                } else {
                    return new ResponseEntity<>("Ошибка добавления вложения.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>("У вас нет прав на добавление вложений", HttpStatus.FORBIDDEN);
    }

}
