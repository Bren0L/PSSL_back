package br.com.banco.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document
public class UserModel {
    @Id
    private String id;
    private String cardNumber;
    private String name;
    private Date bornDate;
    private String cpf;
    private String password;
    private float balance;
    private List<TransactionModel> transactionModel;

    public UserModel(String cardNumber, String name, Date bornDate, String cpf, String password) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.bornDate = bornDate;
        this.cpf = cpf;
        this.password = password;
    }

    public UserModel() {

    }

    public void addTransactionModel(TransactionModel transactionModel) {
        this.transactionModel.add(transactionModel);
    }
}