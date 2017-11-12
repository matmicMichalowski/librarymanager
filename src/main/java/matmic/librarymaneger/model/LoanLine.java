package matmic.librarymaneger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"user", "bookLoans", "magazineLoans"})
@Entity
public class LoanLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanLine")
    private Set<MagazineLoan> magazineLoans = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanLine")
    private Set<BookLoan> bookLoans = new HashSet<>();

    public LoanLine() {
    }

    public LoanLine(User user) {
        this.user = user;
    }

    public void addBookLoan(BookLoan bookLoan){
        bookLoan.setLoanLine(this);
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
