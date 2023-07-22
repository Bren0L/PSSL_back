package br.com.banco.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionDTO {
    @NotBlank
    private String receptor;
    @NotBlank
    private String operator;
    @NotBlank
    private float value;
    @NotBlank
    private String type;
}
