package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.business.AuthBO;
import it.java.course.esercitazione1.exception.ResourceAlreadyPresentException;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.payload.request.LoginRequest;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import it.java.course.esercitazione1.payload.response.MessageResponse;
import it.java.course.esercitazione1.payload.response.UserInfoResponse;
import it.java.course.esercitazione1.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthBO authBO;

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
        try {
            authBO.registerU(signUpRequest);
        } catch (ResourceAlreadyPresentException e) {
            throw new ResourceAlreadyPresentException("Error: Username/Email is already in use!");
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Error: Role is not found.");
        }
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
