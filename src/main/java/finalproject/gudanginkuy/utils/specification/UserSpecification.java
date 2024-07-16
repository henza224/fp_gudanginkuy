package finalproject.gudanginkuy.utils.specification;

import finalproject.gudanginkuy.a_model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserSpecification {

    public static Specification<User> getSpecification(
            String username
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (username!= null
                    && !username.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        root.get("username"), "%" + username + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}