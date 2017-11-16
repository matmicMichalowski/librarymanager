package matmic.librarymaneger.model;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanDate;

    @OneToOne
    private Book book;

    @ManyToOne
    private LibraryAccount libraryAccount;

    public BookLoan() {
        setLoanDate();
    }

    public void setLoanDate(){
        SimpleDateFormat formater = new SimpleDateFormat("dd/M/yyyy");
        Date currentDate = new Date();

        loanDate = formater.format(currentDate);
    }

    public void setBook(Book book){
        if (book != null){
            this.book = book;
            book.setBookLoan(this);
        }
    }

}
