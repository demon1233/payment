package com.example.exercisses.payment.domain;

import com.example.exercisses.payment.adapters.dto.PaymentDTO;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

class PaymentDtoConverter {


    Payment convert(PaymentDTO paymentDTO) {
        UUID uuid = Generators.timeBasedGenerator().generate();
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setId(uuid);
        payment.setBankAccountNumber(paymentDTO.getBankAccountNumber());
        payment.setCurrency(paymentDTO.getCurrency());
        payment.setUserId(paymentDTO.getUserId());
        return payment;
    }

    PaymentDTO convert(Payment payment) {
        return PaymentDTO.builder().userId(payment.getUserId())
                .amount(payment.getAmount())
                .bankAccountNumber(payment.getBankAccountNumber())
                .currency(payment.getCurrency()).id(payment.getId()).build();
    }
}
