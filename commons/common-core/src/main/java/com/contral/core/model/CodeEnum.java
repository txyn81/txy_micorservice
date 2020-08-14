package com.contral.core.model;


public enum CodeEnum {
    SUCCESS(0),
    ERROR(1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
