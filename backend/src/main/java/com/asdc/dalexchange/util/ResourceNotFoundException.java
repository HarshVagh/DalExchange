package com.asdc.dalexchange.util;

public class ResourceNotFoundException extends RuntimeException{


    String resourceName;
    String fieldName;
    long fieldValue;
    public ResourceNotFoundException(String massage) {
        super(massage);
    }
}
