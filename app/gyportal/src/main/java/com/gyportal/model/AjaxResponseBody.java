package com.gyportal.model;

import com.gyportal.model.Enum.Status;

import java.io.Serializable;

/**
 * @author: lihuan
 * Describe: 响应封装
 */
public class AjaxResponseBody implements Serializable{

    private String msg;

    private Object data;

    private String token;

    private String status;

    public static class Builder {

        private String status = Status.OK;

        private Object data = null;

        private String token = null;

        private String msg = "";

        public Builder buildStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder buildMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder buildToken(String token) {
            this.token = token;
            return this;
        }

        public Builder buildData(Object data) {
            this.data = data;
            return this;
        }

        public AjaxResponseBody build() {
            return new AjaxResponseBody(this);
        }
    }

    public AjaxResponseBody(Builder builder) {
        this.status = builder.status;
        this.msg = builder.msg;
        this.data = builder.data;
        this.token = builder.token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
