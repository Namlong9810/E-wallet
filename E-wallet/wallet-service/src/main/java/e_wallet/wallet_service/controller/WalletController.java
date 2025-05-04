package e_wallet.wallet_service.controller;

import e_wallet.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.example.dto.req.CreateWalletDTO;
import org.example.dto.req.WalletReq;
import org.example.dto.res.WalletDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("create")
    public void create(@RequestBody CreateWalletDTO createWalletDTO){
        walletService.createWallet(createWalletDTO);
    }

    @PutMapping("deposit")
    public WalletDTO deposit(@RequestBody WalletReq walletReq){
        return walletService.deposit(walletReq.getWallet_id(), walletReq.getAmount());
    }

    @PutMapping("withdraw")
    public WalletDTO withdraw(@RequestBody WalletReq walletReq){
        return walletService.withdraw(walletReq.getWallet_id(), walletReq.getAmount());
    }
}
