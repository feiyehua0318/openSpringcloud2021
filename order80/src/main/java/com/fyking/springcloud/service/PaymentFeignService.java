package com.fyking.springcloud.service;


import com.fyking.springcloud.entities.CommonResult;
import com.fyking.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "PAYMENT-SERVICE" /*,fallback = PaymentFeignService.class*/)
public interface PaymentFeignService {
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);


    @GetMapping("payment/feign/timeout")
    public String paymentFeignTimeout();

    @GetMapping("payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(Integer id);
    @GetMapping("payment/hystrix/ok/{id}")
    public String paymentInfo_OK(Integer id);
}
