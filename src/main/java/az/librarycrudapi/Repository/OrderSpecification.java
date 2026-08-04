package az.librarycrudapi.Repository;

import az.librarycrudapi.Entity.Order;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class OrderSpecification {

    public static Specification<Order> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Order> hasMemberId(Long memberId) {
        return (root, query, criteriaBuilder) -> {
            if (memberId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("member").get("id"), memberId);
        };
    }

    public static Specification<Order> createdAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), date);
        };
    }
}
