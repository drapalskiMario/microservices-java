package br.com.mdrapalski.transactionbff.api;

import br.com.mdrapalski.transactionbff.service.LimitsService;
import br.com.mdrapalski.transactionbff.dto.DailyLimitDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RestController
@RequestMapping("/limits")
public class LimitsController {

    private LimitsService service;

    public LimitsController(LimitsService service) {
        this.service = service;
    }

    @GetMapping("/{bankBranch}/{account}")
    public Mono<DailyLimitDto> findByBankBranchAndAccount(@PathVariable Long bankBranch, @PathVariable Long account) {
        return service.findDailyLimit(bankBranch, account);
    }
}
