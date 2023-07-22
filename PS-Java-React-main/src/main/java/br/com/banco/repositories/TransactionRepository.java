package br.com.banco.repositories;

import br.com.banco.models.TransactionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends MongoRepository<TransactionModel, String> {
    List<TransactionModel> findByOperatorIdContainsOrReceptorIdContainsOrTransactionDateGreaterThanOrTransactionDateLessThan(
            String operatorId,
            String receptorId,
            LocalDate transactionDateStart,
            LocalDate transactionDateEnd
    );
}
