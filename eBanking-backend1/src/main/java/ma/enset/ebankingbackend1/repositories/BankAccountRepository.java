package ma.enset.ebankingbackend1.repositories;

import ma.enset.ebankingbackend1.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
