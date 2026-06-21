package com.example.socialmedia;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FireBaseConfig {

    @PostConstruct
    public void init() {

        try {

            String firebaseJson = System.getenv("FIREBASE_CREDENTIALS");

            if (firebaseJson == null || firebaseJson.isEmpty()) {
                System.out.println("FIREBASE_CREDENTIALS not found");
                return;
            }

            InputStream stream = new java.io.ByteArrayInputStream(
                    firebaseJson.getBytes(java.nio.charset.StandardCharsets.UTF_8)
            );

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
