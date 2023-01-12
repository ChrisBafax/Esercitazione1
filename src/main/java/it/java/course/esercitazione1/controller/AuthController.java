package it.java.course.esercitazione1.controller;

// Import from other packages
import it.java.course.esercitazione1.business.impl.AuthBOImpl;

import it.java.course.esercitazione1.payload.request.LoginRequest;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import it.java.course.esercitazione1.payload.response.MessageResponse;
import it.java.course.esercitazione1.payload.response.UserInfoResponse;

import it.java.course.esercitazione1.security.services.UserDetailsImpl;

// Import from SpringFrameWork
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

// Import from Javax
import javax.validation.Valid;

// Import from Java
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthBOImpl authBO;

    @PostMapping("/sign-in")
    // Sign in
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserDetailsImpl userDetails = authBO.authUser(loginRequest);
        ResponseCookie jwtCookie = authBO.authCookie(userDetails);
        List<String> roles = authBO.authRoles(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/sign-up")
    // Create a new user
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authBO.registerU(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/sign-out")
    // Logout
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = authBO.authOut();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
