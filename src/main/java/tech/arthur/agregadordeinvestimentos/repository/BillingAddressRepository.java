package tech.arthur.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.arthur.agregadordeinvestimentos.entity.Account;
import tech.arthur.agregadordeinvestimentos.entity.BillingAddress;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
