package finalproject.gudanginkuy.utils.specification;

import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.c_service.ItemService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionSpecification {
    private final ItemService itemService;

    public Specification<Transaction> getSpecification(
            TransactionType type,
            LocalDateTime timestamp,
            Integer itemId
    ){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if (type != null && !type.toString().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (timestamp != null && !timestamp.toString().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("timestamp"),  timestamp));
            }
            if (itemId != null && itemId.toString().isEmpty()){
                Item item = itemService.getOne(itemId);
                predicates.add(criteriaBuilder.equal(root.get("item"), item));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();

        };
    }
}
