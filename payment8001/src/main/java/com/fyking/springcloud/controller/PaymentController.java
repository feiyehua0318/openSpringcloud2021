package com.fyking.springcloud.controller;


import com.fyking.springcloud.entities.CommonResult;
import com.fyking.springcloud.entities.Payment;
import com.fyking.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String ServerPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private PaymentService paymentService;




    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);

        if (result > 0) {
            return new CommonResult(200,"成功添加" + ServerPort,result);
        }else {
            return new CommonResult(444,"添加失败",null);
        }

    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);

        if (payment != null){
            return new CommonResult(200,"查询成功 ,端口是"+ ServerPort,payment);
        }else {
            return new CommonResult(444,"查询失败",null);
        }

    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: "+element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("payment8001");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }

        return this.discoveryClient;
    }



    @GetMapping("payment/feign/timeout")
    public String paymentFeignTimeout(){
        //暂停秒
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return ServerPort;

    }

    @GetMapping("payment/hystrix/timeout/{id}")
    public String paymentHystrixTimeout(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_TimeOut(id);
        return result;
    }

    @GetMapping("payment/hystrix/ok/{id}")
    public String paymentHystrixOK(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_OK(id);
        return result;
    }


}
