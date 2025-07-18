package e_wallet.wallet_service.repository;


import e_wallet.shared_module.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByUserId(UUID user_id);

    List<Wallet> findAllByUserId(UUID userId);

}
