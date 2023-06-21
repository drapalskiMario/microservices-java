package br.com.mdrapalski.transactionbff.api;

import br.com.mdrapalski.transactionbff.domain.LimitsService;
import br.com.mdrapalski.transactionbff.dto.DailyLimitDto;
import br.com.mdrapalski.transactionbff.feign.LimitsClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limits")
public class LimitsController {

    private LimitsService service;

    public LimitsController(LimitsService service) {
        this.service = service;
    }

    @GetMapping("/{bankBranch}/{account}")
    public DailyLimitDto find(@PathVariable Long bankBranch, @PathVariable Long account) {
        return service.findDailyLimit(bankBranch, account);
    }
}
