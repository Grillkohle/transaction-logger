package transaction.logger.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transaction.logger.persistence.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> { }
