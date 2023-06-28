package br.com.mdrapalski.transactionsvc.api;

import br.com.mdrapalski.transactionsvc.dto.TransactionDto;
import br.com.mdrapalski.transactionsvc.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/{bankBranch}/{account}")
    public List<TransactionDto> find(@PathVariable Long bankBranch, @PathVariable Long account) {
        return service.findByAccount(bankBranch, account);
    }
}
