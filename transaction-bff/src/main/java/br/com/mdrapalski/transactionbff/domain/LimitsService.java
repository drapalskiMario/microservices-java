package br.com.mdrapalski.transactionbff.domain;

import br.com.mdrapalski.transactionbff.dto.DailyLimitDto;
import br.com.mdrapalski.transactionbff.feign.LimitsClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Supplier;

@Service
public class LimitsService {

    @Autowired
    private CircuitBreaker countCircuitBreaker;
    private LimitsClient client;


    public LimitsService(LimitsClient client) {
        this.client = client;
    }

    public DailyLimitDto findDailyLimit(final Long bankBranch, final Long account) {
        var daiLyLimitSup = fallback(bankBranch, account);
        return daiLyLimitSup.get();
    }

    private Supplier<DailyLimitDto> fallback(final Long bankBranch, final Long account) {
        var daiLyLimitSup = countCircuitBreaker
                .decorateSupplier(() -> client.findDailyLimit(bankBranch, account));

        return Decorators
                .ofSupplier(daiLyLimitSup)
                .withCircuitBreaker(countCircuitBreaker)
                .withFallback(Arrays.asList(CallNotPermittedException.class), e -> this.getStaticLimit())
                .decorate();
    }

    private DailyLimitDto getStaticLimit() {
        var dailyLimit = new DailyLimitDto();
        dailyLimit.setValue(BigDecimal.ZERO);
        return dailyLimit;
    }
}
