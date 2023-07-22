package br.com.banco.controllers;

import br.com.banco.dtos.TransactionDTO;
import br.com.banco.dtos.UserDTO;
import br.com.banco.models.TransactionModel;
import br.com.banco.models.UserModel;
import br.com.banco.services.TransactionService;
import br.com.banco.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final TransactionService transactionService;
    private final UserService userService;

    public UserController(UserService userService, TransactionService transactionService){
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Object> newUser(@RequestBody @Valid UserDTO userDTO){
        if(userService.existsByUsername(userDTO.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Client Already exists.");
        if(userService.existsByUserCpf(userDTO.getCpf()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF is already in use.");


        String cardNumber = userService.generateCreditCard();

        UserModel userModel = new UserModel(
                cardNumber,
                userDTO.getName(),
                LocalDate.parse(userDTO.getBornDate()),
                userDTO.getCpf(),
                userDTO.getPassword()
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userModel));
        
    }

    @GetMapping("/login{cardNumber}{password}")
    public ResponseEntity<Object> getUser(@RequestParam Map<String, String> params){
        Optional<UserModel> userModelOptional = userService.findByCardNumberAndPassword(params.get("cardNumber"), params.get("password"));

        return userModelOptional.<ResponseEntity<Object>>map(userModel ->
                ResponseEntity.status(HttpStatus.OK).body(userModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado"));
    }

    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "cardNumber")String cardNumber){
        Optional<UserModel> userModelOptional = userService.findByCardNumber(cardNumber);

        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        userService.delete(userModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

    @PutMapping("/{cardNumber}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "cardNumber")String cardNumber, @RequestBody @Valid UserDTO userDTO){
        Optional<UserModel> userModelOptional = userService.findByCardNumber(cardNumber);

        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);

        userModel.setCardNumber(userModelOptional.get().getCardNumber());
        userModel.setBalance(userModelOptional.get().getBalance());
        userModel.setCpf(userModelOptional.get().getCpf());
        userModel.setBornDate(userModelOptional.get().getBornDate());

        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @PutMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransactionDTO transactionDto){
        Optional<UserModel> userModelOptionalOperator = userService.findByCardNumber(transactionDto.getOperator());
        Optional<UserModel> userModelOptionalReceptor = userService.findByCardNumber(transactionDto.getReceptor());

        if(userModelOptionalOperator.isEmpty() || userModelOptionalReceptor.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");


        TransactionModel transactionModel = new TransactionModel(
                LocalDate.now(),
                userModelOptionalReceptor.get(),
                userModelOptionalOperator.get(),
                transactionDto.getValue(),
                transactionDto.getType()
        );

        return ResponseEntity.status(HttpStatus.OK).body(transactionService.save(transactionModel));
    }

    @GetMapping("/transactions{from}{to}{receptor}{operator}")
    public ResponseEntity<Object> getTransactions(@RequestParam Map<String, String> params){
        Optional<UserModel> userModelOptionalOperator = userService.findByCardNumber(params.get("operator"));
        Optional<UserModel> userModelOptionalReceptor = userService.findByCardNumber(params.get("receptor"));

        if(userModelOptionalReceptor.isEmpty()){
            userModelOptionalReceptor = Optional.empty();
        }

        if(userModelOptionalOperator.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        LocalDate from = LocalDate.EPOCH;
        LocalDate to = LocalDate.now();
        try{
            from = LocalDate.parse(params.get("from"));
            to = LocalDate.parse(params.get("to"));
        }catch (DateTimeParseException ignored){}


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService
                        .findByOperatorIdContainsOrReceptorIdContainsOrTransactionDateGreaterThanOrTransactionDateLessThan(
                                userModelOptionalOperator.get().getId(),
                                userModelOptionalReceptor.map(UserModel::getId).orElse("null"),
                                from,
                                to
                        )
                );
    }

}
