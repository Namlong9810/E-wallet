package e_wallet.wallet_service.controller;

import e_wallet.wallet_service.service.WalletService;
import e_wallet.wallet_service.dto.req.CreateWalletDTO;
import e_wallet.wallet_service.dto.req.WalletReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("user/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("create")
    public void create(CreateWalletDTO createWalletDTO){
        walletService.createWallet(createWalletDTO);
    }

    @PutMapping("deposit")
    public void deposit(WalletReq walletReq){
        walletService.deposit(walletReq.getUser_id(), walletReq.getAmount());
    }

    @PutMapping("withdraw")
    public void withdraw(WalletReq walletReq){
        walletService.withdraw(walletReq.getUser_id(), walletReq.getAmount());
    }
}
