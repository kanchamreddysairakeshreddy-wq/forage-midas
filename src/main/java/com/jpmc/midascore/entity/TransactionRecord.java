package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue
    private Long id;
    private float incentive;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    private float amount;

    protected TransactionRecord() {
    }

    public TransactionRecord(
            UserRecord sender,
            UserRecord recipient,
            float amount,
            float incentive
    ) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }
}