package matmic.librarymaneger.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String city;
    private String address;
    private String postCode;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private LibraryAccount userLibraryAccount;

    @OneToMany
    private Set<Loan> loanLine;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
