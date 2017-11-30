package matmic.librarymaneger.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(exclude = {"user", "bookLoans", "magazineLoans"})
@Entity
public class LibraryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Loan loanLine;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libraryAccount")
    private Set<MagazineLoan> magazineLoans = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libraryAccount")
    private Set<BookLoan> bookLoans = new HashSet<>();


    public LibraryAccount() {
    }

    public LibraryAccount(User user) {
        this.user = user;
    }

    public void addBookLoan(BookLoan bookLoan){
        bookLoan.setLibraryAccount(this);
        bookLoans.add(bookLoan);
    }
}
