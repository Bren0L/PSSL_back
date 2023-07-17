package br.com.banco.services;

import br.com.banco.models.TransactionModel;
import br.com.banco.models.UserModel;
import br.com.banco.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

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

    public TransactionModel TransactionDateGreaterThanAndTransactionDateLessThanAndReceptorContains(LocalDate from, LocalDate to, String receptor){
        return repository.TransactionDateGreaterThanAndTransactionDateLessThanAndReceptorContains(from, to, receptor);
    }
}
