package br.com.banco.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;


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
