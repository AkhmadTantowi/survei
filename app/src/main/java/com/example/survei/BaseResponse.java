package com.example.survei;

import com.google.gson.annotations.SerializedName;

public class BaseResponse <T>{

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("data")
    private T data;

    public BaseResponse(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
