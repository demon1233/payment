package com.example.exercisses.payment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    private UUID id;
    private String currency;
    private String userId;
    private long bankAccountNumber;
    private float amount;

    public Payment() {
    }
}
