package com.example.bangbang_gotgot.member.exception;

public class CustomExceptions {

    public static class Exception extends RuntimeException {
        public Exception(String message) { super(message);}
    }

    public static class SmsCertificationNumberMismatchException extends RuntimeException{
        public SmsCertificationNumberMismatchException(String message){super(message);}
    }
}
