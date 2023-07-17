package br.com.banco.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserModel {
    @Id
    //@CreditCardNumber(ignoreNonDigitCharacters = true)
    @Column(name = "cardNumber", nullable = false, length = 19, unique = true, updatable = false)
    private String cardNumber;
    @Column(name = "name", nullable = false, unique = true, length = 70)
    private String name;
    @Column(name = "bornDate", nullable = false, updatable = false, length = 10)
    private String bornDate;
    @Column(name = "cpf", nullable = false, updatable = false, unique = true, length = 14)
    private String cpf;
    @Column(name = "password", nullable = false, length = 16)
    private String password;
    @Column(name = "balance")
    private float balance;
    @OneToMany
    private List<TransactionModel> transactionModel = new ArrayList<>();


    public UserModel(String name, String bornDate, String cpf, String cardNumber, String password) {
        this.name = name;
        this.bornDate = bornDate;
        this.cpf = cpf;
        this.cardNumber = cardNumber;
        this.password = password;
        this.balance = 0;
    }

    public UserModel() {}

    public void addTransaction(TransactionModel transactionModel) {
        this.transactionModel.add(new TransactionModel(LocalDate.now(), transactionModel.getReceptor(), transactionModel.getOperator(), transactionModel.getValue(), transactionModel.getType()));
    }

    public List<TransactionModel> getTransactions(){
        return transactionModel;
    }

    public void sumBalance(float value){
        this.balance += value;
    }

    public void subBalance(float value){
        this.balance -= value;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}