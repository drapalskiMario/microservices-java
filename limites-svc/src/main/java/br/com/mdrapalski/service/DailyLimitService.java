package br.com.mdrapalski.service;

import br.com.mdrapalski.entities.DailyLimit;
import br.com.mdrapalski.repository.DailyLimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DailyLimitService {

    private BigDecimal DEFAULT_LIMIT_VALUE = BigDecimal.valueOf(2000);

    private DailyLimitRepository dailyLimitRepository;

    public DailyLimitService(DailyLimitRepository dailyLimitRepository) {
        this.dailyLimitRepository = dailyLimitRepository;
    }

    public Optional<DailyLimit> findById(Long id) {
        return this.dailyLimitRepository.findById(id);
    }

    public DailyLimit findOrCreate(final Long bankBranch, final Long account) {
        var dailyLimitExists = dailyLimitRepository.findByBankBranchAndAccount(bankBranch, account);
        if (dailyLimitExists.isPresent()) {
            return dailyLimitExists.get();
        }

        var dailyLimit = new DailyLimit();
        dailyLimit.setAccount(account);
        dailyLimit.setBankBranch(bankBranch);
        dailyLimit.setValue(DEFAULT_LIMIT_VALUE);

        return this.dailyLimitRepository.save(dailyLimit);
    }
}
