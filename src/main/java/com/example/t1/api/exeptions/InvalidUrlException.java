package com.example.t1.api.exeptions;

public class InvalidUrlException extends  Exception{
    public InvalidUrlException(String message) {
        super(message);
    }
}
