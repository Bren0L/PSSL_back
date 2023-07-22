package br.com.banco.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
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
}
