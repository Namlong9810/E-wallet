package e_wallet.transaction_service.controller;


import e_wallet.transaction_service.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.dto.req.TransactionReq;
import org.example.entity.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("create")
    public Transaction createTransaction(@RequestBody TransactionReq transactionReq, HttpServletRequest httpServletRequest){
        return transactionService.createTransaction(transactionReq, httpServletRequest);
    }


    @GetMapping("infor")
    public Transaction getInforTransaction(@RequestBody TransactionReq transactionReq){
        return transactionService.getInforTransaction(transactionReq.getSender_id());
    }

    @GetMapping("list")
    public List<Transaction> getListTransactionById(@RequestBody TransactionReq transactionReq){
        return transactionService.getListTransactionById(transactionReq.getSender_id());
    }
}
