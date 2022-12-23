package uz.pdp.jwtappauditingemail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.jwtappauditingemail.entity.User;
import uz.pdp.jwtappauditingemail.entity.enums.RoleName;
import uz.pdp.jwtappauditingemail.payload.ApiResponse;
import uz.pdp.jwtappauditingemail.payload.RegisterDto;
import uz.pdp.jwtappauditingemail.repository.RoleRepository;
import uz.pdp.jwtappauditingemail.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;
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
        //biznes logikani yozamiz
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

        user.setEmailCode(String.valueOf(UUID.randomUUID()));

        userRepository.save(user);
        //EMAIL GA XABAR YUBORISH METHODINI CHAQIRYABMIZ
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Muvafaqiyatli ro'yxatdan o'tdingiz akkountingiz aktivlashtirilishi uchun " +
                "emailingizni tasqdiqlang", true);
    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage
                    .setFrom("dilafruzabduqodirova2@gmail.com");
            mailMessage
                    .setTo("mmm743050@gmail.com");
            mailMessage
                    .setSubject("Account ni Tasdiqlash ! ");
            mailMessage
                    .setText("<a href = 'http://localhost:8080/api/auth/" +
                            "verifyEmail?emailCode=" + emailCode + "+&email=" + sendingEmail + "'>Tasdiqlang</a>");
            javaMailSender
                    .send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account tasdiqlandi ",true);
        }
        return new ApiResponse("Account allaqachon tasdiqlangan",false);
    }
}
