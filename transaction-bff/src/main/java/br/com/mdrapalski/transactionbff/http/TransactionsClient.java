package br.com.mdrapalski.transactionbff.http;

import br.com.mdrapalski.transactionbff.dto.DailyLimitDto;
import br.com.mdrapalski.transactionbff.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "transactions", url = "${url.transactions}")
public interface TransactionsClient {

    @GetMapping(path = "transactions/{bankBranch}/{account}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<TransactionDto> findTransactions(@PathVariable Long bankBranch, @PathVariable Long account);
}
