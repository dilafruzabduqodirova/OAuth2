package uz.pdp.appaouth2facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class AppAouth2FacebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppAouth2FacebookApplication.class, args);
    }

}
