package e_wallet.transaction_service.repository;


import e_wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT MAX(t.transaction_date) FROM Transaction t WHERE t.userId = :userId AND t.walletId = :walletId")
    Instant findPreviousTransactionDate(@Param("userId") UUID userId, @Param("walletId") UUID walletId);


    @Query("SELECT COUNT(t) FROM Transaction t  " +
            " WHERE t.userId = :userId " +
            " AND t.transaction_date >= :time ")
    Integer countTransactionIn5minByUserId(@Param("userId") UUID userId, @Param("time") Instant period);

    List<Transaction> findAllByWalletId(UUID wallet_id);
}
