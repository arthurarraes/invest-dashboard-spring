package tech.arthur.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.arthur.agregadordeinvestimentos.entity.AccountStock;
import tech.arthur.agregadordeinvestimentos.entity.AccountStockId;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
