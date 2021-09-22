package com.fyking.springcloud.service;


import com.fyking.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    public int create(Payment payment);
    public Payment getPaymentById(@Param("id") Long id);
    public String paymentInfo_TimeOut(Integer id);
    public String paymentInfo_OK(Integer id);
}
