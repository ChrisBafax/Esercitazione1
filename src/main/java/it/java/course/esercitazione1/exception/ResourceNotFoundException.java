package it.java.course.esercitazione1.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUDI = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
