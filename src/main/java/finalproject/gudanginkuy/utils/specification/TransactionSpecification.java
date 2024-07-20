package finalproject.gudanginkuy.utils.specification;

import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.c_service.ItemService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionSpecification {
    private final ItemService itemService;

    public Specification<Transaction> getSpecification(
            TransactionType type,
            LocalDate date,
            String  itemName
    ){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if (type != null && !type.toString().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (date != null && !date.toString().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("date"),  date));
            }
            if (itemName != null && !itemName.isEmpty()) {
                Join<Transaction, Item> joinItem = root.join("item", JoinType.INNER);
                predicates.add(criteriaBuilder.like(joinItem.get("name"), "%" + itemName + "%"));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();

        };
    }
}
