package com.example.IndoorTurfBookingGround.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDataExistsException extends Exception{

    private String error;

    public UserDataExistsException(String error)
    {
         super(error);
         this.error = error;
    }

    public UserDataExistsException(){}

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
