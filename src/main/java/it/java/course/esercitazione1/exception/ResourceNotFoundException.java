package it.java.course.esercitazione1.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUDI = 1L;

    // Exception when not founding a resource. Output with custom message.
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
