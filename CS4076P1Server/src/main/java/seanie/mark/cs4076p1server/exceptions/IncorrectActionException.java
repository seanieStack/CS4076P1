package seanie.mark.cs4076p1server.exceptions;

public class IncorrectActionException extends Exception {

    private final String message;
    
    public IncorrectActionException(String message) {
        this.message = message;
    }
    
    public String getMessage(){
        return this.message;
    }
    }