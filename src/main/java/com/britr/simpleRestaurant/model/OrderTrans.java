package com.britr.simpleRestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_trans")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
@NoArgsConstructor
public class OrderTrans implements Serializable {
    @Id
    @Column(name = "order_id", nullable = false, length = 45)
    private String id;

    @Column(name = "order_received_dateTime")
    private LocalDateTime orderReceivedDatetime;

    @Column(name = "order_Status", length = 45)
    private String orderStatus;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "payment_status", length = 45)
    private String paymentStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderReceivedDatetime() {
        return orderReceivedDatetime;
    }

    public void setOrderReceivedDatetime(LocalDateTime orderReceivedDatetime) {
        this.orderReceivedDatetime = orderReceivedDatetime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}