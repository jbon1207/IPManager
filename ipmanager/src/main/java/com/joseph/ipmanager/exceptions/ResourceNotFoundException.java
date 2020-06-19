package com.joseph.ipmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Resource not found")
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(long id){
        super("Resource not found. ID: "+id);
    }

    public ResourceNotFoundException(String type, long id){
        super(type+" was not found. ID: "+id);
    }
    
}
