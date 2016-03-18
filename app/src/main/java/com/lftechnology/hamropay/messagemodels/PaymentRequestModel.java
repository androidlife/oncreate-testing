package com.lftechnology.hamropay.messagemodels;

/**
 * Created by ManasShrestha on 2/11/16.
 */
public class PaymentRequestModel {

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public enum PaymentStatus {
        PENDING,
        ACCEPTED,
        FAILED,
        CANCELED
    }

    private String requestedBy;
    private String requestedAmount;
    private Boolean alignRight;
    private String messageTime;
    private PaymentStatus paymentStatus;

    public PaymentRequestModel(String requestedBy, String requestedAmount,String messageTime,PaymentStatus paymentStatus ,Boolean alignRight){
        setRequestedAmount(requestedAmount);
        setRequestedBy(requestedBy);
        setMessageTime(messageTime);
        setAlignRight(alignRight);
        setPaymentStatus(paymentStatus);
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Boolean isAlignRight() {
        return alignRight;
    }

    public void setAlignRight(Boolean alignRight) {
        this.alignRight = alignRight;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
