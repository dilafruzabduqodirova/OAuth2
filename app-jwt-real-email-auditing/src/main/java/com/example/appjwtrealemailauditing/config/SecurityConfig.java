package com.example.appjwtrealemailauditing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender
                .setHost("smtp:gmail.com");
        mailSender
                .setPort(587);
        mailSender
                .setUsername("dilafruzabduqodirova2@gmail.com");
        mailSender
                .setPassword("dilafruz123");
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smpt");
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.debug",true);
        return mailSender;

    }
}
