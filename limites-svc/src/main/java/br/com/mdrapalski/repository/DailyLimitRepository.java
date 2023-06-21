package br.com.mdrapalski.repository;

import br.com.mdrapalski.entities.DailyLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyLimitRepository extends CrudRepository<DailyLimit, Long> {

    Optional<DailyLimit> findByBankBranchAndAccount(Long bankBranch, Long account);
}
