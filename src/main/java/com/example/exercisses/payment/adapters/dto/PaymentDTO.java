package com.example.exercisses.payment.adapters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class PaymentDTO extends RepresentationModel<PaymentDTO> {
    private UUID id;
    private String currency;
    private String userId;
    private long bankAccountNumber;
    private float amount;

    PaymentDTO() {
        super();
    }

    PaymentDTO(Link initialLink) {
        super(initialLink);
    }
}
