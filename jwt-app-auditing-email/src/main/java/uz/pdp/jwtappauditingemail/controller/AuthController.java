package uz.pdp.jwtappauditingemail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.jwtappauditingemail.payload.ApiResponse;
import uz.pdp.jwtappauditingemail.payload.RegisterDto;
import uz.pdp.jwtappauditingemail.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?>registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?>verifyEmail(@RequestParam String emailCode, @RequestParam String email){
         ApiResponse apiResponse = authService.verifyEmail(emailCode,email);
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
