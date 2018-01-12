package com.andersenlab.lunoexchangebtc.network;

public class BaseResponse<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
