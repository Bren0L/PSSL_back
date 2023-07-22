package br.com.banco.services;

import br.com.banco.models.TransactionModel;
import br.com.banco.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }

    @Transactional
    public TransactionModel save(TransactionModel transactionModel){
        return repository.save(transactionModel);
    }

    public List<TransactionModel> findByOperatorIdContainsOrReceptorIdContainsOrTransactionDateGreaterThanOrTransactionDateLessThan(
            String operatorId,
            String receptorId,
            LocalDate from,
            LocalDate to
    ){

        return repository.findByOperatorIdContainsOrReceptorIdContainsOrTransactionDateGreaterThanOrTransactionDateLessThan(
                operatorId,
                receptorId,
                from,
                to
        );
    }

}
