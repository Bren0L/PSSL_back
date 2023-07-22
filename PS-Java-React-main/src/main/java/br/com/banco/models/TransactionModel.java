package br.com.banco.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "transaction")
public class TransactionModel {
    @Id
    private String id;
    private LocalDate transactionDate;
    private UserModel operator;
    private UserModel receptor;
    private float value;
    private String type;

    public TransactionModel(LocalDate transactionDate, UserModel operator, UserModel receptor, float value, String type) {
        this.transactionDate = transactionDate;
        this.operator = operator;
        this.receptor = receptor;
        this.value = value;
        this.type = type;
    }
}
