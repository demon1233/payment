package com.example.exercisses.payment.domain;

import com.example.exercisses.payment.PaymentApplication;
import com.example.exercisses.payment.adapters.persistence.PaymentOperationRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PaymentApplication.class)
class PaymentServiceConfiguration {

    @Bean
    PaymentServiceImpl paymentService(PaymentOperationRepositoryImpl repository) {
        return new PaymentServiceImpl(repository);
    }
}
