package com.shop.exception.order;

public class OrderAccessDeniedException extends RuntimeException{
    public OrderAccessDeniedException(String message){
        super(message);
    }
}
