package az.librarycrudapi.Repository;

import az.librarycrudapi.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.orderItems oi GROUP BY b.id HAVING SUM(oi.quantity) >= :minQty")
    List<Book> findPopularBooksByMinOrderQuantity(@Param("minQty") Long minQty);
}
