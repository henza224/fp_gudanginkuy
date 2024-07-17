package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends
        JpaRepository<Transaction, Integer>,
        JpaSpecificationExecutor<Transaction> {
}
