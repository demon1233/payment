package com.example.exercisses.payment.adapters.persistence;

import com.example.exercisses.payment.domain.Payment;
import com.example.exercisses.payment.domain.PaymentOperationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class PaymentOperationRepositoryImpl implements PaymentOperationPort {

    @Autowired
    private PaymentRepository repository;

    @Override
    public Optional<Payment> load(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Payment> loadAll() {
        List<Payment> payments = new ArrayList<>();
        repository.findAll().forEach(payments::add);
        return payments;
    }

    @Override
    public void save(Payment payment) {
        repository.save(payment);
    }

    @Override
    public void remove(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }
}
