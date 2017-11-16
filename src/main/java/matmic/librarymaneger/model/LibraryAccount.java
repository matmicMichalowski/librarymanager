package matmic.librarymaneger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"user", "bookLoans", "magazineLoans"})
@Entity
public class LibraryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(BookLoan  bk: bookLoans){
            sb.append(bk.getBook().getTitle());
            sb.append(", ");
        }
        return sb.toString();
    }
}
