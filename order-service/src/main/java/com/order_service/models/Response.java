package com.order_service.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String responseMessage = null;
    private boolean success;
    private T data;

    public static <T> Response<T> success(T data, String message) {
        return new Response<>(message, true, data);
    }

    public static <T> Response<T> error(String errorMessage) {
        return new Response<>(errorMessage, false, null);
    }

}