package finalproject.gudanginkuy.utils.specification;

import finalproject.gudanginkuy.a_model.Item;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> getSpecification(
            String name, Integer quantity
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()){
                predicates.add(criteriaBuilder.like(
                        root.get("name"), "%" + name + "%"));
            }
            if (quantity != null && !quantity.toString().isEmpty()){
                Predicate quantityPredicates;
                if (quantity >= 0 && quantity <= 50){
                    quantityPredicates = criteriaBuilder.lessThanOrEqualTo(
                            root.get("quantity"), 50);
                } else if (quantity <= 100) {
                    quantityPredicates = criteriaBuilder.between(
                            root.get("quantity"), 51, 100);
                } else if (quantity <= 300) {
                    quantityPredicates = criteriaBuilder.between(
                            root.get("quantity"), 101, 300);
                } else {
                    quantityPredicates = criteriaBuilder.between(
                            root.get("quantity"), 301, quantity);
                }
                predicates.add(quantityPredicates);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
