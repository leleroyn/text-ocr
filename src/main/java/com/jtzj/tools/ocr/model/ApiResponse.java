package com.jtzj.tools.ocr.model;

import lombok.Data;

import java.util.Map;

@Data
public class ApiResponse {
    private int code;
    private String msg;
    private Map data;

    public ApiResponse(){
        this.code = ResponseState.Success;
    }
}
