package tech.arthur.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadordeinvestimentos.dto.CreateStockDto;
import tech.arthur.agregadordeinvestimentos.dto.CreateUserDto;
import tech.arthur.agregadordeinvestimentos.dto.UserResponseDto;
import tech.arthur.agregadordeinvestimentos.entity.Stock;
import tech.arthur.agregadordeinvestimentos.entity.User;
import tech.arthur.agregadordeinvestimentos.service.StockService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto){
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Stock>> listStocks(){
        var stocks = stockService.listStocks();

        return ResponseEntity.ok(stocks);
    }
}
