package br.com.mdrapalski.transactionbff.service;

import br.com.mdrapalski.transactionbff.dto.DailyLimitDto;
import br.com.mdrapalski.transactionbff.http.LimitsClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public Mono<DailyLimitDto> findDailyLimit(final Long bankBranch, final Long account) {
        return findDailyLimitSupplier(bankBranch, account);
    }

    private Mono<DailyLimitDto> findDailyLimitSupplier(final Long bankBranch, final Long account) {
        var daiLyLimitSup = countCircuitBreaker
                .decorateSupplier(() -> client.findDailyLimit(bankBranch, account));

        return Mono
                .fromSupplier(
                        Decorators
                                .ofSupplier(daiLyLimitSup)
                                .withCircuitBreaker(countCircuitBreaker)
                                .withFallback(Arrays.asList(CallNotPermittedException.class), e -> this.getStaticLimit())
                                .decorate()
                );

    }

    private DailyLimitDto getStaticLimit() {
        var dailyLimit = new DailyLimitDto();
        dailyLimit.setValue(BigDecimal.ZERO);
        return dailyLimit;
    }
}
