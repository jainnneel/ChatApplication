package com.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GroupBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    
    private String orderId;
    
    private String groupId;
    
    @ManyToOne
    private UserEntity entity;
    
    private String paymentId;
    
    private String status;
    
    private Date dateOfPayment;
    
    private Time timeOfPayment;

    public GroupBooking() {
        super();
    }

    public GroupBooking(Long bookingId, String orderId, String groupId, UserEntity entity, String paymentId,
            String status) {
        super();
        this.bookingId = bookingId;
        this.orderId = orderId;
        this.groupId = groupId;
        this.entity = entity;
        this.paymentId = paymentId;
        this.status = status;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public UserEntity getEntity() {
        return entity;
    }

    public void setEntity(UserEntity entity) {
        this.entity = entity;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public Time getTimeOfPayment() {
        return timeOfPayment;
    }

    public void setTimeOfPayment(Time timeOfPayment) {
        this.timeOfPayment = timeOfPayment;
    }

    @Override
    public String toString() {
        return "GroupBooking [bookingId=" + bookingId + ", orderId=" + orderId + ", groupId=" + groupId + ", entity="
                + entity + ", paymentId=" + paymentId + ", status=" + status + "]";
    }
    
}
