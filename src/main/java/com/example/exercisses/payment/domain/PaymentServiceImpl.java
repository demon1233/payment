package com.example.exercisses.payment.domain;

import com.example.exercisses.payment.adapters.dto.PaymentDTO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final static PaymentDtoConverter PAYMENT_HELPERS = new PaymentDtoConverter();
    private final PaymentOperationPort paymentOperationPort;

    @Override
    public void create(PaymentDTO paymentDTO) {
        Payment payment = PAYMENT_HELPERS.convert(paymentDTO);
        paymentOperationPort.save(payment);
    }

    @Override
    public PaymentDTO fetch(UUID id) {
        return paymentOperationPort.load(id).map(PAYMENT_HELPERS::convert)
                .orElseThrow(() -> new RuntimeException(String.format("Not existing payment with id: %s", id)));
    }

    @Override
    public List<PaymentDTO> fetchAll() {
        return paymentOperationPort.loadAll().stream().map(PAYMENT_HELPERS::convert).collect(Collectors.toList());
    }

    @Override
    public void remove(UUID id) {
        paymentOperationPort.remove(id);
    }

    @Override
    public void removeAll() {
        paymentOperationPort.removeAll();
    }

    @Override
    public PaymentDTO update(UUID id, PaymentDTO paymentDTO) {
        Optional<Payment> payment = paymentOperationPort.load(id);

        if (payment.isPresent()) {
            Payment paymentTmp = this.update(payment.get(), paymentDTO);
            paymentOperationPort.save(paymentTmp);
            return PAYMENT_HELPERS.convert(paymentTmp);
        }
        throw new RuntimeException(String.format("Not existing payment with id: %s", id));
    }

    private Payment update(Payment payment, PaymentDTO paymentDTO) {
        payment.setUserId(paymentDTO.getUserId());
        payment.setCurrency(paymentDTO.getCurrency());
        payment.setBankAccountNumber(paymentDTO.getBankAccountNumber());
        payment.setAmount(paymentDTO.getAmount());
        return payment;
    }
}

