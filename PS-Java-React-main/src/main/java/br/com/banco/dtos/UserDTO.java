package br.com.banco.dtos;


import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {
    @NotBlank
    @Size(min = 10)
    private String name;
    @NotBlank
    @Size(min = 10)
    private String bornDate;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    @Size(min = 5)
    private String password;


    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public String getBornDate(){
        return bornDate;
    }

    public void setBornDate(String bornDate){
        this.bornDate = bornDate;
    }
}
