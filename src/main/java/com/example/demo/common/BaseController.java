package com.example.demo.common;

/**
 * 基础controller
 * */
public abstract class BaseController {
    protected String retContent(int status,Object data) {
        return ReturnFormat.retParam(status, data);
    }
}
