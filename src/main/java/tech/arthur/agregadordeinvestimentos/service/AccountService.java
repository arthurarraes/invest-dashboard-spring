package tech.arthur.agregadordeinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.arthur.agregadordeinvestimentos.client.BrapiClient;
import tech.arthur.agregadordeinvestimentos.dto.AccountStockResponseDto;
import tech.arthur.agregadordeinvestimentos.dto.AssociateStockDto;
import tech.arthur.agregadordeinvestimentos.entity.AccountStock;
import tech.arthur.agregadordeinvestimentos.entity.AccountStockId;
import tech.arthur.agregadordeinvestimentos.repository.AccountRepository;
import tech.arthur.agregadordeinvestimentos.repository.AccountStockRepository;
import tech.arthur.agregadordeinvestimentos.repository.StockRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private String TOKEN = "gZiqdmLxrMg7BhS7kSoP3C";
    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;
    private BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


    public void associateStock(String accountId, AssociateStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var existing = accountStockRepository.findById(id);

        AccountStock accountStock;
        if (existing.isPresent()) {
            accountStock = existing.get();
            accountStock.setQuantity(accountStock.getQuantity() + dto.quantity());
        } else {
            accountStock = new AccountStock(id, account, stock, dto.quantity());
        }

        accountStockRepository.save(accountStock);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var getStocks = account.getAccountStocks()
                .stream()
                .map(accountStock -> new AccountStockResponseDto(
                        accountStock.getStock().getStockId(),
                        accountStock.getQuantity(),
                        getTotal( accountStock.getQuantity(),
                                accountStock.getStock().getStockId())
                        ))
                .toList();
        return getStocks;
    }

    private double getTotal(Integer quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);
        var price = response.results().getFirst().regularMarketPrice();
        return quantity * price;
    }
}
