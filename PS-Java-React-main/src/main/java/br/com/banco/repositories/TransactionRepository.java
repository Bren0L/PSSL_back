package br.com.banco.repositories;

import br.com.banco.models.TransactionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;

public interface TransactionRepository extends MongoRepository<TransactionModel, String> {
    TransactionModel TransactionDateGreaterThanAndTransactionDateLessThanAndReceptorContains(LocalDate from, LocalDate to, String receptor);
}
