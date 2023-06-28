package br.com.mdrapalski.transactionbff.http;

import br.com.mdrapalski.transactionbff.dto.DailyLimitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "limits", url = "${url.limits}")
public interface LimitsClient {

    @GetMapping(path = "/daily-limit/{bankBranch}/{account}", produces = MediaType.APPLICATION_JSON_VALUE)
    DailyLimitDto findDailyLimit(@PathVariable Long bankBranch, @PathVariable Long account);
}
