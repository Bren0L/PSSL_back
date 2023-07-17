package br.com.banco.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Transaction")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;
    @Column(name = "transaction_date", nullable = false, updatable = false)
    private LocalDate transactionDate;
    @Column(name = "operator", length = 70, updatable = false, nullable = false)
    private String operator;
    @Column(name = "receptor", length = 70, updatable = false, nullable = false)
    private String receptor;
    @Column(name = "value", updatable = false)
    private float value;
    @Column(name = "type", nullable = false, updatable = false)
    private String type;


    public TransactionModel(LocalDate transactionDate, String receptor, String operator, float value, String type) {
        this.transactionDate = transactionDate;
        this.receptor = receptor;
        this.operator = operator;
        this.value = value;
        this.type = type;
    }

    public TransactionModel() {}

    public UUID getId() {
        return id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getOperator() {
        return operator;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", transactionDate='" + transactionDate + '\'' +
                ", receptor=" + receptor +
                ", value=" + value +
                ", type='" + type + '\'' +
                '}';
    }
}
