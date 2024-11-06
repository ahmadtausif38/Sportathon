package com.hcl.elch.sportathon.admin.customException;

import lombok.Value;

public class ValueNotPresentException extends Exception{
    public ValueNotPresentException(){}
    public ValueNotPresentException(String str){super(str);}
}
