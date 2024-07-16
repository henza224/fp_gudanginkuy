package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepository extends
        JpaRepository<Stock, Integer>,
        JpaSpecificationExecutor<Stock> {
}
