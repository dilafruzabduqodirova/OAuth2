package com.example.appjwtrealemailauditing.service;

import com.example.appjwtrealemailauditing.entity.User;
import com.example.appjwtrealemailauditing.entity.enums.RoleName;
import com.example.appjwtrealemailauditing.payload.ApiResponse;
import com.example.appjwtrealemailauditing.payload.RegisterDto;
import com.example.appjwtrealemailauditing.repository.RoleRepository;
import com.example.appjwtrealemailauditing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;


    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday email allaqachon mavjud", false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        //email ga xabar yuborish method ni chaqiryabmiz
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Muvafiqaytli ro'yxatdan o'tdingiz ," +
                " accountingiz aktivlashtirilishi uchun email gizni tasdiqlang",true);

    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("pdpUz@gmail.com");
            mailMessage.setTo("dilafruzabduqodirova2@gmail.com");
            mailMessage.setSubject("ACCOUNT NI TASDIQLASH");
            mailMessage.setText("<a href = 'http://localhost:8080/api/auth/verifyEmail?emailCode=" +
                    emailCode + "+&email=" + sendingEmail + "'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
