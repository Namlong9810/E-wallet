package e_wallet.wallet_service.controller;

import e_wallet.wallet_service.service.WalletService;
import e_wallet.wallet_service.dto.req.CreateWalletDTO;
import e_wallet.wallet_service.dto.req.WalletReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("create")
    public void create(@RequestBody CreateWalletDTO createWalletDTO){
        walletService.createWallet(createWalletDTO);
    }

    @PutMapping("deposit")
    public void deposit(@RequestBody WalletReq walletReq){
        walletService.deposit(walletReq.getWallet_id(), walletReq.getAmount());
    }

    @PutMapping("withdraw")
    public void withdraw(@RequestBody WalletReq walletReq){
        walletService.withdraw(walletReq.getWallet_id(), walletReq.getAmount());
    }
}
