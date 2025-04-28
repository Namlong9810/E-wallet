package e_wallet.transaction_service.controller;

import e_wallet.transaction_service.dto.req.TransactionReq;
import e_wallet.transaction_service.entity.Transaction;
import e_wallet.transaction_service.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("create")
    public Transaction createTransaction(@RequestBody TransactionReq transactionReq, HttpServletRequest httpServletRequest){
        return transactionService.createTransaction(transactionReq, httpServletRequest);
    }
}
