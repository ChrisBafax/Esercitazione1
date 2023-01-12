package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.LoginRequest;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import it.java.course.esercitazione1.security.services.UserDetailsImpl;
import org.springframework.http.ResponseCookie;

import java.util.List;

public interface AuthBO {

    public User registerU(SignupRequest signUpRequest);

    public UserDetailsImpl authUser(LoginRequest loginRequest);

    public ResponseCookie authCookie(UserDetailsImpl userDetails);

    public List<String> authRoles(UserDetailsImpl userDetails);

    public ResponseCookie authOut();
}
