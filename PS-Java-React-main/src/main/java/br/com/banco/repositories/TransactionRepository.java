package br.com.banco.repositories;

import br.com.banco.models.TransactionModel;
import br.com.banco.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<TransactionModel, String> {
    TransactionModel TransactionDateGreaterThanAndTransactionDateLessThanAndReceptorContains(LocalDate from, LocalDate to, String receptor);
}
