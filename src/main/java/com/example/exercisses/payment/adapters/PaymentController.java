package com.example.exercisses.payment.adapters;

import com.example.exercisses.payment.adapters.dto.PaymentDTO;
import com.example.exercisses.payment.domain.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping(value = "/payments", consumes = "application/json", produces = "application/json")
    HttpEntity<PaymentDTO> create(@RequestBody PaymentDTO paymentDTO) {
        paymentService.create(paymentDTO);
        paymentDTO.add(linkTo(methodOn(PaymentController.class).create(paymentDTO)).withSelfRel());
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/payments/{id}")
    HttpEntity<PaymentDTO> fetch(@PathVariable final UUID id) {
        PaymentDTO paymentDTO = paymentService.fetch(id);
        paymentDTO.add(linkTo(methodOn(PaymentController.class).fetch(id)).withSelfRel());
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/payments/{id}")
    HttpEntity<PaymentDTO> remove(@PathVariable final UUID id) {
        paymentService.remove(id);
        PaymentDTO paymentDTO = PaymentDTO.builder().build();
        paymentDTO.add(linkTo(methodOn(PaymentController.class).remove(id)).withSelfRel());
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/payments/{id}")
    HttpEntity<PaymentDTO> update(@PathVariable final UUID id, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO paymentDTO1 = paymentService.update(id, paymentDTO);
        paymentDTO.add(linkTo(methodOn(PaymentController.class).update(id, paymentDTO1)).withSelfRel());
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/payments")
    HttpEntity<PaymentDTO> removeAll() {
        paymentService.removeAll();
        PaymentDTO paymentDTO = PaymentDTO.builder().build();
        paymentDTO.add(linkTo(methodOn(PaymentController.class).removeAll()).withSelfRel());
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/payments")
    HttpEntity<List<PaymentDTO>> fetchAll() {
        List<PaymentDTO> paymentDTOs = paymentService.fetchAll();
        paymentDTOs.forEach(paymentDTO ->
                paymentDTO.add(linkTo(methodOn(PaymentController.class).fetchAll()).withSelfRel()));
        return new ResponseEntity<>(paymentDTOs, HttpStatus.OK);
    }
}
