package az.librarycrudapi.Repository;

import az.librarycrudapi.Entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @NonNull
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book", "member"})
    Page<Order> findAll(@NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book", "member"})
    Page<Order> findAll(Specification<Order> spec, @NonNull Pageable pageable);
}
