package ma.enset.ebankingbackend1.repositories;

import ma.enset.ebankingbackend1.entities.AccountOperation;
import ma.enset.ebankingbackend1.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
 List<AccountOperation> findByBankAccountId(String accountId);
 Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
