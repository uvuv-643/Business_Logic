package ru.uvuv643.business_logic.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageService {

    public Optional<String> storeBase64Image(String base64Image) {
        byte[] data = Base64.decodeBase64(base64Image);
        if (base64Image.charAt(0) == 'i' || base64Image.charAt(0) == '/') {
            try {
                String storagePath = "public/";
                if (!Files.exists(Path.of(storagePath))) {
                    Files.createDirectory(Path.of(storagePath));
                }
                String path = UUID.randomUUID() + ".png";
                OutputStream stream = new FileOutputStream(storagePath + path);
                stream.write(data);
                stream.close();
                return Optional.of(path);
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

}
