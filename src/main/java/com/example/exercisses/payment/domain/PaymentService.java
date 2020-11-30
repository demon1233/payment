package com.example.exercisses.payment.domain;

import com.example.exercisses.payment.adapters.dto.PaymentDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    void create(PaymentDTO payment);

    PaymentDTO fetch(UUID id);

    List<PaymentDTO> fetchAll();

    void remove(UUID id);

    void removeAll();

    PaymentDTO update(UUID id, PaymentDTO paymentDTO);
}
