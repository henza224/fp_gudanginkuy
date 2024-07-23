package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends
        JpaRepository<Item, Integer>,
        JpaSpecificationExecutor <Item> {
    Optional<Item> findByBarcode(String barcode);
}
