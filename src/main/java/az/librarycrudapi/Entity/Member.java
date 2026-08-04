package az.librarycrudapi.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "members")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "borrowedBy", fetch = FetchType.LAZY)
    private List<Book> borrowedBooks;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders;
}
