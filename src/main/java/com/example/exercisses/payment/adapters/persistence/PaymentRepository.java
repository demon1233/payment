package com.example.exercisses.payment.adapters.persistence;

import com.example.exercisses.payment.domain.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface PaymentRepository extends CrudRepository<Payment, UUID> {

}
