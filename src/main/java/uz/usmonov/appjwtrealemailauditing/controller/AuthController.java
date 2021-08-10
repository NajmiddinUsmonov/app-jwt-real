package uz.usmonov.appjwtrealemailauditing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.usmonov.appjwtrealemailauditing.payload.ApiResponse;
import uz.usmonov.appjwtrealemailauditing.payload.LoginDto;
import uz.usmonov.appjwtrealemailauditing.payload.RegisterDto;
import uz.usmonov.appjwtrealemailauditing.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser ( registerDto );
        return ResponseEntity.status ( apiResponse.isSuccess ()?201:409 ).body ( apiResponse );
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode,@RequestParam String email){

        ApiResponse apiResponse=authService.verifyEmail(emailCode,email);
        return ResponseEntity.status ( apiResponse.isSuccess ()?200:409 ).body ( apiResponse );

    }

    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto){
        ApiResponse login = authService.login ( loginDto );
        return ResponseEntity.status (login.isSuccess ()?200:401).body ( login );
    }



}
