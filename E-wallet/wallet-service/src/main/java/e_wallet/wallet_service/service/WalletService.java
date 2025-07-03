package e_wallet.wallet_service.service;

import e_wallet.wallet_service.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import e_wallet.shared_module.dto.req.CreateWalletDTO;
import e_wallet.shared_module.dto.res.WalletDTO;
import e_wallet.shared_module.entity.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletService {
    @Autowired
    WalletRepository walletRepository;
    ModelMapper modelMapper;

    public void createWallet(CreateWalletDTO createWalletDTO){
        Wallet wallet = Wallet.builder()
                .userId(createWalletDTO.getUser_id())
                .walletName(createWalletDTO.getWalletName())
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
        dto.setWalletName(updatedWallet.getWalletName());
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
        dto.setWalletName(updatedWallet.getWalletName());
        dto.setBalance(updatedWallet.getBalance());

        return dto;
    }

    public List<WalletDTO> getListWalletByUserId(String userId){
        List<Wallet> listWallet = walletRepository.findAllByUserId(UUID.fromString(userId));
        return listWallet.stream()
                .map(data -> modelMapper.map(data, WalletDTO.class))
                .collect(Collectors.toList());
    }
}
