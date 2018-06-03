package com.leige.blog.common.utils.result;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by leige
 */
public class Result<T> {

    private boolean state;

    private Integer code;

    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
