package com.example.exercisses.payment.adapters;

import com.example.exercisses.payment.adapters.dto.PaymentDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class PaymentControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final PaymentDTO paymentDTO = PaymentDTO.builder()
            .amount(10).currency("Euro").bankAccountNumber(94394349).userId("userId").build();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldPassOptimisticAcceptanceFlow() throws Exception {
        //given-when-then
        Assertions.assertEquals(0, fetchAllPayments().size());

        createPayment();
        createPayment();

        Assertions.assertEquals(2, fetchAllPayments().size());
        UUID idFirstPaymentDTO = fetchAllPayments().get(0).getId();

        deletePayment(idFirstPaymentDTO.toString());

        List<PaymentDTO> paymentsAfterDelete = fetchAllPayments();
        Assertions.assertEquals(1, paymentsAfterDelete.size());
        long newBankAccountNumber = 1111;

        updatePayment(paymentsAfterDelete.get(0).getId().toString(), paymentWithUpdatedBankAccountNumber(newBankAccountNumber));

        PaymentDTO paymentDTOWithUpdatedBankAccountNumber = fetchAllPayments().get(0);
        Assertions.assertEquals(newBankAccountNumber, paymentDTOWithUpdatedBankAccountNumber.getBankAccountNumber());
    }

    private PaymentDTO paymentWithUpdatedBankAccountNumber(long bankAccountNumber) {
        return PaymentDTO.builder()
                .amount(10).currency("Euro").bankAccountNumber(bankAccountNumber).userId("userId").build();
    }

    private List<PaymentDTO> fetchAllPayments() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/payments"))
                .andExpect(status().isOk()).andReturn().getResponse();

        return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<PaymentDTO>>() {
        });
    }

    private void createPayment() throws Exception {
        mockMvc.perform(
                post("/api/payments")
                        .content(asJsonString(paymentDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void updatePayment(String id, PaymentDTO newPaymentDTO) throws Exception {
        mockMvc.perform(
                put("/api/payments/" + id)
                        .content(asJsonString(newPaymentDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void deletePayment(String id) throws Exception {
        mockMvc.perform(
                delete("/api/payments/" + id))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}