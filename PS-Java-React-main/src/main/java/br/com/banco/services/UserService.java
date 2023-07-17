package br.com.banco.services;

import br.com.banco.models.UserModel;
import br.com.banco.repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByCardNumber(String cardNumber){
        return userRepository.existsByCardNumber(cardNumber);
    }

    public Optional<UserModel> findByName(String name){
        return userRepository.findByName(name);
    }

    public String generateCreditCard(){
        String generatedCardNumber;
        do{
            generatedCardNumber = "";
            for(int i = 0; i < 4; i++){
                int n = (int) (Math.random()*10000);

                generatedCardNumber += String.valueOf(n);
                if(i != 3)
                    generatedCardNumber += "-";
            }
        }while(!isCreditCardValid(generatedCardNumber));

        return generatedCardNumber;
    }

    private boolean isCreditCardValid(String cardNumber) {
        String cn = cardNumber.replace("-", "");
        int a[] = {cardNumber.length()%2 == 0? 1 : 2};
        return cn.chars().map(i -> i - '0').map(n -> n * (a[0] = a[0] == 1? 2 : 1)).map(n -> n > 9? n-9 : n).sum()%10 == 0;
    }

    public boolean existsByUserCpf(String cpf){
        return userRepository.existsByCpf(cpf);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByName(username);
    }

    public Optional<UserModel> findUserModelByCardNumberAndPassword(String cardNumber, String password){
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
