package com.shop.exception.coupon;

public class CouponAlreadyUsedException extends RuntimeException{
    public CouponAlreadyUsedException(String message){
        super(message);
    }

}
