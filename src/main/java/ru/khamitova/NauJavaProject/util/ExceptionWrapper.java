package ru.khamitova.NauJavaProject.util;

public class ExceptionWrapper {
    private String message;

    private ExceptionWrapper(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public static ExceptionWrapper create(Throwable e){
        return new ExceptionWrapper(e.getMessage());
    }

    public static ExceptionWrapper create(String message){
        return new ExceptionWrapper(message);
    }
}
