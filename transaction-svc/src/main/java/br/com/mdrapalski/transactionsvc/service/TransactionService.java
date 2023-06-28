package br.com.mdrapalski.transactionsvc.service;

import br.com.mdrapalski.transactionsvc.dto.AccountDto;
import br.com.mdrapalski.transactionsvc.dto.StatusType;
import br.com.mdrapalski.transactionsvc.dto.TransactionDto;
import br.com.mdrapalski.transactionsvc.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void save(final TransactionDto transactionDto) {
        if (Objects.isNull(transactionDto.getDate())) {
            transactionDto.setDate(LocalDateTime.now());
        }
        repository.save(transactionDto);
        log.info("Transaction saved {}", transactionDto);
    }


    public void approveTransaction(TransactionDto transactionDto) {
        var transactionOptional = repository.findById(transactionDto.getId());
        if (transactionOptional.isEmpty()) {
            return;
        }

        var transaction = transactionOptional.get();
        if (transactionDto.getStatus() == StatusType.ANALYZED && transaction.getStatus() != StatusType.ANALYZED) {
            transaction.setStatus(StatusType.APPROVED);
            repository.save(transaction);
            log.info("Approved transaction {}", transaction);
        }
    }

    public List<TransactionDto> findByAccount(final Long bankBranch, final Long accountCode) {
        var account = new AccountDto();
        account.setBankBranchCode(bankBranch);
        account.setAccountCode(accountCode);

        return repository.findByAccountDto(account);
    }
}
