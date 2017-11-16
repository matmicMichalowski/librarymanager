package matmic.librarymaneger.model;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

;

@Data
@Entity
public class MagazineLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanDate;

    @OneToOne
    private Magazine magazine;

    @ManyToOne
    private LibraryAccount libraryAccount;

    public MagazineLoan() {
        setLoanDate();
    }

    public void setLoanDate(){
        SimpleDateFormat formater = new SimpleDateFormat("dd/M/yyyy");
        Date currentDate = new Date();

        loanDate = formater.format(currentDate);

    }
}
