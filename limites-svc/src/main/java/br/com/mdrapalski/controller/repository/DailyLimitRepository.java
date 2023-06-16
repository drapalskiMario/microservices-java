package br.com.mdrapalski.controller.repository;

import br.com.mdrapalski.entities.DailyLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLimitRepository extends CrudRepository<DailyLimit, Long> {

    DailyLimit findByBankBranchAndAccount(Long bankBranch, Long account);
}
