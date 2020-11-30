package com.example.exercisses.payment.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PaymentStore implements PaymentOperationPort {

    private final HashMap<UUID, Payment> db = new HashMap();

    @Override
    public void save(Payment payment) {
        db.put(payment.getId(), payment);
    }

    @Override
    public Optional<Payment> load(UUID id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Payment> loadAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void remove(UUID id) {
        db.remove(id);
    }

    @Override
    public void removeAll() {
        db.values().removeAll(db.values());
    }
}
