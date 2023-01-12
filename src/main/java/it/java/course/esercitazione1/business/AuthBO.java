package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.LoginRequest;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import it.java.course.esercitazione1.security.services.UserDetailsImpl;
import org.springframework.http.ResponseCookie;

import java.util.List;

public interface AuthBO {

    User registerU(SignupRequest signUpRequest);

    UserDetailsImpl authUser(LoginRequest loginRequest);

    ResponseCookie authCookie(UserDetailsImpl userDetails);

    List<String> authRoles(UserDetailsImpl userDetails);

    ResponseCookie authOut();
}
