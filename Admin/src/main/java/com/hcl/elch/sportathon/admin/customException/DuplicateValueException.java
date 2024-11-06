package com.hcl.elch.sportathon.admin.customException;

public class DuplicateValueException extends Exception{
    public DuplicateValueException(){

    }
    public DuplicateValueException(String str){
        super(str);
    }
}
