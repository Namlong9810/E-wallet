package e_wallet.wallet_service.service;

import e_wallet.wallet_service.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.dto.req.CreateWalletDTO;
import org.example.dto.res.WalletDTO;
import org.example.entity.Wallet;
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

    public WalletDTO deposit(UUID wallet_id, BigDecimal amount) {
        Wallet wallet = null;
        try {wallet = walletRepository.findById(wallet_id)
                    .orElseThrow(() -> new BadRequestException("Not found"));
            wallet.setBalance(wallet.getBalance().add(amount));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        Wallet updatedWallet =  walletRepository.save(wallet);

        WalletDTO dto = new WalletDTO();
        dto.setWalletId(updatedWallet.getWalletId());
        dto.setUserId(updatedWallet.getUserId());
        dto.setBalance(updatedWallet.getBalance());

        return dto;
    }

    public WalletDTO withdraw(UUID wallet_id, BigDecimal amount){
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

        Wallet updatedWallet =  walletRepository.save(wallet);

        WalletDTO dto = new WalletDTO();
        dto.setWalletId(updatedWallet.getWalletId());
        dto.setUserId(updatedWallet.getUserId());
        dto.setBalance(updatedWallet.getBalance());

        return dto;
    }
}
