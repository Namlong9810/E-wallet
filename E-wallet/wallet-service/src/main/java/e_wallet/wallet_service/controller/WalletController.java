package e_wallet.wallet_service.controller;

import e_wallet.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import e_wallet.dto.req.CreateWalletDTO;
import e_wallet.dto.req.WalletReq;
import e_wallet.dto.res.WalletDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("list")
    public List<WalletDTO> getListWalletByUserId(@RequestParam String userId){
        return walletService.getListWalletByUserId(userId);
    }
}
