package br.com.mdrapalski.controller;

import br.com.mdrapalski.entities.DailyLimit;
import br.com.mdrapalski.service.DailyLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DailyLimitController {

    private DailyLimitService dailyLimitService;

    public DailyLimitController(DailyLimitService dailyLimitService){
        this.dailyLimitService = dailyLimitService;
    }

    @GetMapping("/daily-limit/{bankBranch}/{account}")
    public DailyLimit findOrCreate(@PathVariable Long bankBranch, @PathVariable Long account) {
        return dailyLimitService.findOrCreate(bankBranch, account);
    }

    @GetMapping("/daily-limit/{id}")
    public DailyLimit findById(@PathVariable Long id) {
        var dayLimit = dailyLimitService.findById(id);

        if (dayLimit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }

        return dayLimit.get();
    }
}
