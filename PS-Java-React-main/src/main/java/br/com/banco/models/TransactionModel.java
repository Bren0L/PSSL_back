package br.com.banco.models;


import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;


public @Data class TransactionModel {
    private UUID id;
    private LocalDate transactionDate;
    private String operator;
    private String receptor;
    private float value;
    private String type;

    public TransactionModel(LocalDate now, String receptor, String name, float value, String type) {
    }
}
