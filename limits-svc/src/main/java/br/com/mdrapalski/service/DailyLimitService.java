package br.com.mdrapalski.service;

import br.com.mdrapalski.dto.StatusType;
import br.com.mdrapalski.dto.TransactionDto;
import br.com.mdrapalski.entities.DailyLimit;
import br.com.mdrapalski.events.DailyLimitEventsProducer;
import br.com.mdrapalski.repository.DailyLimitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyLimitService {

    private static final Logger log = LoggerFactory.getLogger(DailyLimitService.class);
    private BigDecimal DEFAULT_LIMIT_VALUE = BigDecimal.valueOf(2000);

    private DailyLimitRepository repository;
    private DailyLimitEventsProducer eventsProducer;

    public DailyLimitService(DailyLimitRepository repository, DailyLimitEventsProducer eventsProducer) {
        this.repository = repository;
        this.eventsProducer = eventsProducer;
    }

    public Optional<DailyLimit> findById(Long id) {
        return this.repository.findById(id);
    }

    public DailyLimit findOrCreate(final Long bankBranch, final Long account) {
        var dailyLimitExists = repository.findByBankBranchAndAccount(bankBranch, account);

        if (dailyLimitExists.isPresent()) {
            return dailyLimitExists.get();
        }

        return create(bankBranch, account);
    }

    public void validateDailyLimit(TransactionDto transactionDto) {
        var dailyLimitExists = repository.findByBankBranchAndAccountAndDate(
                transactionDto.getAccountDto().getBankBranchCode(),
                transactionDto.getAccountDto().getAccountCode(),
                LocalDate.now()
        );

        DailyLimit dailyLimit;

        if (dailyLimitExists.isPresent()) {
            dailyLimit = dailyLimitExists.get();
        } else {
            dailyLimit = create(transactionDto.getAccountDto().getBankBranchCode(), transactionDto.getAccountDto().getAccountCode());
        }

        if (dailyLimit.getValue().compareTo(transactionDto.getValue()) < 0) {
            transactionDto.setStatus(StatusType.SUSPECTED_FRAUD);
            log.info("The transaction amount is greater than the daily limit {}", transactionDto);
            eventsProducer.send(transactionDto);

            return;
        }

        if (dailyLimit.getValue().compareTo(BigDecimal.valueOf(10000)) > 0) {
            transactionDto.setStatus(StatusType.UNDER_HUMAN_ANALYSIS);
            log.info("Transaction under human analysis {}", transactionDto);

            return;
        }

        transactionDto.setStatus(StatusType.ANALYZED);
        log.info("Transaction analyzed {}", transactionDto);

        dailyLimit.setValue(dailyLimit.getValue().subtract(transactionDto.getValue()));
        repository.save(dailyLimit);

        eventsProducer.send(transactionDto);
    }

    private DailyLimit create(final Long bankBranch, final Long account) {
        var dailyLimit = new DailyLimit();
        dailyLimit.setAccount(account);
        dailyLimit.setBankBranch(bankBranch);
        dailyLimit.setValue(DEFAULT_LIMIT_VALUE);
        dailyLimit.setDate(LocalDate.now());

        return repository.save(dailyLimit);
    }
}
