package tech.arthur.agregadordeinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.arthur.agregadordeinvestimentos.dto.*;
import tech.arthur.agregadordeinvestimentos.entity.Account;
import tech.arthur.agregadordeinvestimentos.entity.BillingAddress;
import tech.arthur.agregadordeinvestimentos.entity.User;
import tech.arthur.agregadordeinvestimentos.repository.AccountRepository;
import tech.arthur.agregadordeinvestimentos.repository.BillingAddressRepository;
import tech.arthur.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){
         var entity = new User(
               null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);
         var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<UserResponseDto> listUsers() {
        return userRepository.findAll().stream().map(user ->
                new UserResponseDto(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getCreationTimeStamp(),
                        user.getUpdatedTimeStamp(),
                        user.getAccounts().stream().map(account ->
                                new AccountResponseDto(account.getAccountId().toString(), account.getDescription())
                        ).toList()
                )
        ).toList();
    }


    public void updateUserById(String userId, UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            var user =  userEntity.get();

            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if(userExists){
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                null,
                user,
                null,
                createAccountDto.description(),
                new ArrayList<>()
        );
        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                null,
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );
        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                .toList();
    }
}
