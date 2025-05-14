package tech.arthur.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadordeinvestimentos.dto.AccountStockResponseDto;
import tech.arthur.agregadordeinvestimentos.dto.AssociateStockDto;
import tech.arthur.agregadordeinvestimentos.dto.CreateAccountDto;
import tech.arthur.agregadordeinvestimentos.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable String accountId, @RequestBody AssociateStockDto associateStockDto){
        accountService.associateStock(accountId, associateStockDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> getAssociateStock(@PathVariable String accountId){
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
