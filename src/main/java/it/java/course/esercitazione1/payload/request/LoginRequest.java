package it.java.course.esercitazione1.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    @Setter
    @Getter
    private String username;

    @NotBlank
    @Getter
    @Setter

    private String password;
}
