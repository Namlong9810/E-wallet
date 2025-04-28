package e_wallet.wallet_service.service;

import e_wallet.wallet_service.repository.WalletRepository;
import e_wallet.wallet_service.dto.req.CreateWalletDTO;
import e_wallet.wallet_service.entity.Wallet;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    public void createWallet(CreateWalletDTO createWalletDTO){
        Wallet wallet = Wallet.builder()
                .userId(createWalletDTO.getUser_id())
                .balance(createWalletDTO.getBalance())
                .build();
        walletRepository.save(wallet);
    }

    public Wallet deposit(UUID wallet_id, BigDecimal amount) {
        Wallet wallet = null;
        try {wallet = walletRepository.findById(wallet_id)
                    .orElseThrow(() -> new BadRequestException("Not found"));
            wallet.setBalance(wallet.getBalance().add(amount));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        return walletRepository.save(wallet);
    }

    public Wallet withdraw(UUID wallet_id, BigDecimal amount){
        Wallet wallet = null;
        try {
            wallet = walletRepository.findById(wallet_id)
                    .orElseThrow(() -> new BadRequestException("Not found"));

            if(wallet.getBalance().compareTo(amount) > 0) {
                wallet.setBalance(wallet.getBalance().subtract(amount));
            }else{
                throw new BadRequestException("Insufficient Funds");
            }
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        return walletRepository.save(wallet);
    }
}
