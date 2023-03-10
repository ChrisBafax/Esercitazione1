package it.java.course.esercitazione1.business.impl;

import it.java.course.esercitazione1.business.AuthBO;
import it.java.course.esercitazione1.exception.ResourceAlreadyPresentException;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.LoginRequest;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import it.java.course.esercitazione1.repository.RoleRepository;
import it.java.course.esercitazione1.repository.UserRepository;
import it.java.course.esercitazione1.security.jwt.JwtUtils;
import it.java.course.esercitazione1.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthBOImpl implements AuthBO {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    // Register a new user with an encrypted password and the appropriate role
    public User registerU(SignupRequest signUpRequest) throws ResourceAlreadyPresentException, ResourceNotFoundException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResourceAlreadyPresentException("Error: Username.");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResourceAlreadyPresentException("Error: Email.");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // Check what the string role equals to
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByRoleType(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByRoleType(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role."));
                        roles.add(userRole);
                    }
                }
            });
        }
        // Set the Role and Save
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    public UserDetailsImpl authUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public ResponseCookie authCookie(UserDetailsImpl userDetails) {
        return jwtUtils.generateJwtCookie(userDetails);
    }

    public List<String> authRoles(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public ResponseCookie authOut() {
        return jwtUtils.getCleanJwtCookie();
    }
}
