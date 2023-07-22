package br.com.banco.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "user")
public class UserModel {
    @Id
    private String id;
    private String cardNumber;
    private String name;
    private LocalDate bornDate;
    private String cpf;
    private String password;
    private float balance;


    public UserModel(String cardNumber, String name, LocalDate bornDate, String cpf, String password) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.bornDate = bornDate;
        this.cpf = cpf;
        this.password = password;
    }

    public UserModel() {}

}