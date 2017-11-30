package matmic.librarymaneger.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanDate;

    @OneToOne
    private Item item;

    @ManyToOne
    private User user;

    public void setLoanDate(){
        SimpleDateFormat formater = new SimpleDateFormat("dd/M/yyyy");
        Date currentDate = new Date();

        loanDate = formater.format(currentDate);
    }

}
