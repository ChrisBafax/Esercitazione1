package it.java.course.esercitazione1.exception;

public class ResourceAlreadyPresentException extends RuntimeException {

    private static final long serialVersionUDI = 2L;

    // Exception when not founding a resource. Output with custom message.
    public ResourceAlreadyPresentException(String msg) {
        super(msg);
    }
}
