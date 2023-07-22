package br.com.banco.services;

import br.com.banco.models.UserModel;
import br.com.banco.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel insert(UserModel userModel){
        return userRepository.insert(userModel);
    }

    public String generateCreditCard(){
        StringBuilder generatedCardNumber;
        do{
            generatedCardNumber = new StringBuilder();
            for(int i = 0; i < 4; i++){
                int n = (int) (Math.random()*10000);

                generatedCardNumber.append(n);
                if(i != 3)
                    generatedCardNumber.append("-");
            }
        }while(!isCreditCardValid(generatedCardNumber.toString()));

        return generatedCardNumber.toString();
    }

    private boolean isCreditCardValid(String cardNumber) {
        String cn = cardNumber.replace("-", "");
        int[] a = {cardNumber.length()%2 == 0? 1 : 2};
        return cn.chars().map(i -> i - '0').map(n -> n * (a[0] = a[0] == 1? 2 : 1)).map(n -> n > 9? n-9 : n).sum()%10 == 0;
    }

    public boolean existsByUserCpf(String cpf){
        return userRepository.existsByCpf(cpf);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByName(username);
    }

    public Optional<UserModel> findByCardNumberAndPassword(String cardNumber, String password){
        return userRepository.findByCardNumberAndPassword(cardNumber, password);
    }

    @Transactional
    public UserModel save(UserModel userModel){
        return userRepository.save(userModel);
    }

    @Transactional
    public void delete(UserModel userModel){
        userRepository.delete(userModel);
    }

    public Optional<UserModel> findByCardNumber(String cardNumber){
        return userRepository.findByCardNumber(cardNumber);
    }
}
