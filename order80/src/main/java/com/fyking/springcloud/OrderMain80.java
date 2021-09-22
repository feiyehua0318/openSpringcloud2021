package com.fyking.springcloud;


import com.fyking.murule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient( name = "PAYMENT-SERVICE",configuration = MySelfRule.class)
@EnableFeignClients
@EnableHystrix
public class OrderMain80 {

     public static void main(String[] args){
             SpringApplication.run(OrderMain80.class,args);
         }
      @Bean
      @LoadBalanced
    public RestTemplate getRestTemplate(){
         return new RestTemplate();
     }
}
