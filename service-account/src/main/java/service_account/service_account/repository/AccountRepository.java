package service_account.service_account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service_account.service_account.model.Account;
import service_account.service_account.model.AccountId;

@Repository
public interface AccountRepository extends JpaRepository<Account, AccountId> {
}
