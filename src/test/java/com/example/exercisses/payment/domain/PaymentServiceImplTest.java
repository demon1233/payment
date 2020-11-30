package com.example.exercisses.payment.domain;

import com.example.exercisses.payment.adapters.dto.PaymentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    PaymentStore paymentStore = new PaymentStore();
    private final PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl(paymentStore);

    @BeforeEach
    public void tearDown() {
        paymentStore.removeAll();
    }

    @Test
    public void shouldCreatePayment() {
        //given
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(10L).userId("userId").build();
        //when
        paymentServiceImpl.create(paymentDTO);
        paymentServiceImpl.create(paymentDTO);
        //then
        Assertions.assertEquals(2, paymentServiceImpl.fetchAll().size());
    }

    @Test
    public void shouldFetchPayment() {
        //given
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(94394349).userId("userId").build();
        paymentServiceImpl.create(paymentDTO);
        List<PaymentDTO> paymentDTOS = paymentServiceImpl.fetchAll();
        //when
        PaymentDTO actualPaymentDTO = paymentServiceImpl.fetch(paymentDTOS.get(0).getId());
        //then
        Assertions.assertEquals(paymentDTOS.get(0), actualPaymentDTO);
    }

    @Test
    public void shouldThrowExceptionWhenFetchNotExistingElement() {
        //when then
        Assertions.assertThrows(RuntimeException.class, () -> {
            paymentServiceImpl.fetch(UUID.fromString("2338d7f6-3239-11eb-adc1-0242ac120002"));
        });
    }

    @Test
    public void shouldThrowExceptionWhenUpdateNotExistingElement() {
        //when then
        Assertions.assertThrows(RuntimeException.class, () -> {
            paymentServiceImpl.update(UUID.fromString("2338d7f6-3239-11eb-adc1-0242ac120002"), PaymentDTO.builder().build());
        });
    }

    @Test
    public void shouldFetchAllPayments() {
        //given
        PaymentDTO createdPaymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(94394349).userId("userId").build();

        paymentServiceImpl.create(createdPaymentDTO);
        paymentServiceImpl.create(createdPaymentDTO);
        paymentServiceImpl.create(createdPaymentDTO);
        //when
        List<PaymentDTO> paymentDTOs = paymentServiceImpl.fetchAll();
        //then
        Assertions.assertEquals(3, paymentDTOs.size());

    }

    @Test
    public void shouldRemovePayment() {
        //given
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(94394349).userId("userId").build();
        paymentServiceImpl.create(paymentDTO);
        PaymentDTO paymentDTO1 = paymentServiceImpl.fetchAll().get(0);
        //when
        paymentServiceImpl.remove(paymentDTO1.getId());
        //then
        Assertions.assertEquals(0, paymentServiceImpl.fetchAll().size());
    }

    @Test
    public void shouldRemovePayments() {
        //given
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(94394349).userId("userId").build();
        paymentServiceImpl.create(paymentDTO);
        paymentServiceImpl.create(paymentDTO);

        Assertions.assertEquals(2, paymentServiceImpl.fetchAll().size());
        //when
        paymentServiceImpl.removeAll();
        //then
        Assertions.assertEquals(0, paymentServiceImpl.fetchAll().size());
    }

    @Test
    public void shouldUpdatePayment() {
        //given
        PaymentDTO initialPaymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(9000).userId("userId").build();
        PaymentDTO updatedPaymentDTO = PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(99999).userId("userId").build();

        paymentServiceImpl.create(initialPaymentDTO);
        PaymentDTO createdPaymentDTO = paymentServiceImpl.fetchAll().get(0);
        //when
        paymentServiceImpl.update(createdPaymentDTO.getId(), updatedPaymentDTO);
        //then
        PaymentDTO actualPaymentDTO = paymentServiceImpl.fetch(createdPaymentDTO.getId());
        Assertions.assertEquals(updatedPaymentDTO.getBankAccountNumber(), actualPaymentDTO.getBankAccountNumber());
    }
}