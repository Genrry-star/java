package com.mrpeng.exception;

import lombok.Data;

@Data
public class IllegalParamException extends RuntimeException {
    private String msg;
    private Integer code;

    public IllegalParamException(String msg) {
        this.msg = msg;
    }
}
