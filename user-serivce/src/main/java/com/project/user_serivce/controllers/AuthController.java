//package com.project.user_serivce.controllers;
//
//import com.project.user_serivce.models.User;
//import com.project.user_serivce.repositories.UserRepository;
//import com.project.user_serivce.services.IJwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//    private final UserRepository userRepository;
//    private final IJwtService jwtService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
//        String email = body.get("email");
//        String password = body.get("password");
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials"));
//        if(!passwordEncoder.matches(password,user.getPassword())){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
//        }
//
//        String token = jwtService.generateToken(user);
//        return ResponseEntity.ok(Map.of("token",token));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Map<String,String> body){
//        String email = body.get("email");
//        String password = body.get("password");
//        String name = body.get("name");
//
//        if(userRepository.findByEmail(email).isPresent()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already in use");
//        }
//
//        User newUser = User.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .build();
//
//        userRepository.save(newUser);
//
//        String token = jwtService.generateToken(newUser);
//        return ResponseEntity.ok(Map.of("token",token));
//    }
//}
