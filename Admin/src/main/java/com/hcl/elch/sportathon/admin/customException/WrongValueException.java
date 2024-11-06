package com.hcl.elch.sportathon.admin.customException;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class WrongValueException extends Exception {

    public WrongValueException(){}

    public WrongValueException(String str){super(str);}

}
