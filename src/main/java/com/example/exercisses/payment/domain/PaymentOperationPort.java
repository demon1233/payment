package com.example.exercisses.payment.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOperationPort {
    void save(Payment payment);

    Optional<Payment> load(UUID id);

    List<Payment> loadAll();

    void remove(UUID id);

    void removeAll();
}
