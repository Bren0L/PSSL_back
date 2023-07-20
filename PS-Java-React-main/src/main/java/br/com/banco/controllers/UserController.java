package br.com.banco.controllers;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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

        UserModel userModel = null;

        try{
            userModel = new UserModel(cardNumber, userDTO.getName(), new SimpleDateFormat("dd/MM/yyyy").parse(userDTO.getBornDate()), userDTO.getCpf(), userDTO.getPassword());
        }catch (ParseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data incorreta.");
        }




        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userModel));
        
    }

    @GetMapping("/login{cardNumber}{password}")
    public ResponseEntity<Object> getUser(@RequestParam Map<String, String> params){
        Optional<UserModel> userModelOptional = userService.findUserModelByCardNumberAndPassword(params.get("cardNumber"), params.get("password"));
        System.out.println("card: "+params.get("cardNumber")+"\npassword: "+params.get("password"));
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
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
        userModel.setTransactionModel(userModelOptional.get().getTransactionModel());

        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @PostMapping("/transfer{cardNumber}")
    public ResponseEntity<Object> transfer(@PathVariable(value = "cardNumber") String cardNumber, @RequestBody TransactionModel transactionModel){
        System.out.println("Entrou");
        Optional<UserModel> userModelOptionalOperator = userService.findByCardNumber(cardNumber);
        System.out.println("Is empty"+userModelOptionalOperator.isEmpty());
        if(userModelOptionalOperator.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        TransactionModel transactionModel1 = new TransactionModel(LocalDate.now(), transactionModel.getReceptor(), userModelOptionalOperator.get().getName(), transactionModel.getValue(), transactionModel.getType());
        userModelOptionalOperator.get().addTransactionModel(transactionModel1);

        return ResponseEntity.status(HttpStatus.OK).body(transactionService.save(transactionModel));
    }

    @GetMapping("/transactions{from}{to}{receptor}")
    public ResponseEntity<Object> getTransactions(@RequestParam Map<String, String> filter, @RequestBody UserModel userModel){
        Optional<UserModel> userModelOptional = userService.findByCardNumber(userModel.getCardNumber());
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");



        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.TransactionDateGreaterThanAndTransactionDateLessThanAndReceptorContains(LocalDate.parse(filter.get("from")), LocalDate.parse(filter.get("to")), filter.get("receptor")));
    }

}
