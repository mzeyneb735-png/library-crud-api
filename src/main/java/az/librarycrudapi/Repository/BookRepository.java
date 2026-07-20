package az.librarycrudapi.Repository;

import az.librarycrudapi.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}