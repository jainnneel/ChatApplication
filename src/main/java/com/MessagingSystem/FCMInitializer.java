package com.MessagingSystem;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.twilio.Twilio;

@Service
public class FCMInitializer {
    
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
    @PostConstruct
    public void initialize() {
        try {
            System.out.println(firebaseConfigPath);
            Twilio.init("xxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxx");
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                        FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
