package com.main.fetchrewards.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    Long id;
    private String payer;
    private int points;
    private Timestamp timestamp;

}
