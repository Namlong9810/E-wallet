package e_wallet.transaction_service.repository;

import e_wallet.transaction_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT MAX(t.createdAt) FROM Transaction t WHERE t.userId = :userId AND t.walletId = :walletId")
    Instant findPreviousTransactionDate(@Param("userId") UUID userId, @Param("walletId") UUID walletId);


    @Query("SELECT COUNT(t) FROM Transaction t  " +
            " WHERE t.userId = :userId " +
            " AND t.createdAt >= :time ")
    Integer countTransactionIn5minByUserId(@Param("userId") UUID userId, @Param("time") Instant period);
}
