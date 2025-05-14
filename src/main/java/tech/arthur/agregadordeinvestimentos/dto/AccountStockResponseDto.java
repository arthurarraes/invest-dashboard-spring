package tech.arthur.agregadordeinvestimentos.dto;

import tech.arthur.agregadordeinvestimentos.entity.AccountStock;

import java.util.List;

public record AccountStockResponseDto(String stockId, int quantity, double total) {
}
