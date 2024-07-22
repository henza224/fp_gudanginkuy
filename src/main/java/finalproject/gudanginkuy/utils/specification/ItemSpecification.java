package finalproject.gudanginkuy.utils.specification;

import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Vendor;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> getSpecification(
            String name, String category, String vendor,  Integer quantity
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
            if (category != null && !category.isEmpty()) {
                Join<Item, Category> joinCategory = root.join("category", JoinType.INNER);
                predicates.add(criteriaBuilder.like(joinCategory.get("categoryName"), "%" + category + "%"));
            }
            if (vendor != null && !vendor.isEmpty()) {
                Join<Item, Vendor> joinVendor = root.join("vendor", JoinType.INNER);
                predicates.add(criteriaBuilder.like(joinVendor.get("vendorName"), "%" + vendor + "%"));
            }



            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
