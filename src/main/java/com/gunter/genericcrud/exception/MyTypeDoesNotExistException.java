package com.gunter.genericcrud.exception;

public class MyTypeDoesNotExistException extends RuntimeException {


    public MyTypeDoesNotExistException(String s) {
        super(s);
    }
}
