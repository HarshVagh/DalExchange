package com.asdc.dalexchange.util;

public class ResourceNotFoundException extends RuntimeException{


    String resourceName;
    String fieldName;
    long fieldValue;
    public ResourceNotFoundException(String massage) {
       /* super(String.format("Resource '%s' not found in field '%s : %s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;*/
        super(massage);
    }
}
