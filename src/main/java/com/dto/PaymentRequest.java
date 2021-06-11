package com.dto;

public class PaymentRequest {

    private String orderId;
    
    private String paymentId;

    public PaymentRequest() {
        super();
    }

    public PaymentRequest(String orderId, String paymentId) {
        super();
        this.orderId = orderId;
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return "PaymentRequest [orderId=" + orderId + ", paymentId=" + paymentId + "]";
    }
    
}
