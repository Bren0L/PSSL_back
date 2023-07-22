package br.com.banco.repositories;

import br.com.banco.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    boolean existsByName(String name);
    boolean existsByCpf(String cpf);
    Optional<UserModel> findByCardNumberAndPassword(String cardNumber, String password);
    Optional<UserModel> findByCardNumber(String cardNumber);
}
