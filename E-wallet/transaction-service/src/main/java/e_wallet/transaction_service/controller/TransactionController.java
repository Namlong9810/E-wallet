package e_wallet.transaction_service.controller;


import e_wallet.transaction_service.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import e_wallet.shared_module.dto.req.TransactionReq;
import e_wallet.shared_module.dto.res.common.ResponseObject;
import e_wallet.shared_module.entity.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("create")
    public ResponseObject<Transaction> createTransaction(@RequestBody TransactionReq transactionReq, HttpServletRequest httpServletRequest){
        return transactionService.createTransaction(transactionReq, httpServletRequest);
    }

    @PostMapping("create/deposit")
    public ResponseObject<Transaction> createDepositTransaction(@RequestBody TransactionReq transactionReq, HttpServletRequest httpServletRequest){
        return transactionService.createDepositTransaction(transactionReq, httpServletRequest);
    }
    @GetMapping("infor")
    public Transaction getInforTransaction(@RequestBody TransactionReq transactionReq){
        return transactionService.getInforTransaction(transactionReq.getSender_id());
    }

    @GetMapping("list")
    public List<Transaction> getListTransactionById(@RequestParam String wallet_id){
        return transactionService.getListTransactionById(UUID.fromString(wallet_id));
    }
}
