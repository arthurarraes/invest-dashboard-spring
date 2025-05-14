package tech.arthur.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.arthur.agregadordeinvestimentos.dto.CreateStockDto;
import tech.arthur.agregadordeinvestimentos.dto.UserResponseDto;
import tech.arthur.agregadordeinvestimentos.entity.Stock;
import tech.arthur.agregadordeinvestimentos.repository.StockRepository;

import java.util.List;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );
        stockRepository.save(stock);
    }

    public List<Stock> listStocks() {
        return stockRepository.findAll().stream().toList();
    }
}
