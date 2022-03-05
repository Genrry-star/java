package com.mrpeng.exception;

import lombok.Data;

@Data
public class NormalException extends RuntimeException {
    private String msg;
    private Integer code;

    public NormalException(String msg) {
        this.msg = msg;
    }

    public NormalException(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
