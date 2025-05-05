package com.shop.exception;

import com.shop.exception.coupon.CouponAlreadyUsedException;
import com.shop.exception.coupon.CouponExpiredException;
import com.shop.exception.coupon.CouponNotFoundException;
import com.shop.exception.coupon.CouponNotOwnedException;
import com.shop.exception.dto.ErrorResponse;
import com.shop.exception.user.UserNotFoundException;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.exception.review.ReviewNotFoundException;
import com.shop.exception.wishlist.WishListNotFoundException;
import com.shop.exception.cart.CartNotFoundException;
import com.shop.exception.category.CategoryNotFoundException;
import com.shop.exception.order.OrderNotFoundException;
import com.shop.exception.order.OrderAccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFound(ReviewNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "REVIEW_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(WishListNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWishListNotFound(WishListNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "WISHLIST_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFound(CartNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "CART_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFound(OrderNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, "COUPON_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(CouponExpiredException.class)
    public ResponseEntity<ErrorResponse> handleCouponExpired(CouponExpiredException e) {
        return buildResponse(HttpStatus.BAD_REQUEST,"COUPON_EXPIRED", e.getMessage());
    }

    @ExceptionHandler(CouponAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleCouponAlreadyUsed(CouponAlreadyUsedException e) {
        return buildResponse(HttpStatus.IM_USED,"COUPON_ALREADY_USED", e.getMessage());
    }

    @ExceptionHandler(CouponNotOwnedException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotOwned(CouponNotOwnedException e) {
        return buildResponse(HttpStatus.BAD_REQUEST,"COUPON_NOT_OWNED", e.getMessage());
    }

    @ExceptionHandler(OrderAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleOrderAccessDenied(OrderAccessDeniedException e) {
        return buildResponse(HttpStatus.FORBIDDEN, "ORDER_ACCESS_DENIED", e.getMessage());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArg(IllegalArgumentException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "알 수 없는 오류가 발생했습니다.");
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String errorCode, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(errorCode, message));
    }
}