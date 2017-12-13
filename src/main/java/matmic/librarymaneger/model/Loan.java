package matmic.librarymaneger.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Setter
@Getter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanDate;

    private boolean beforeDeadline;

    @OneToOne
    private Item item;

    @ManyToOne
    private User user;

    @ManyToOne
    private Employee employee;

    public Loan(){
        this.setLoanDate();
        this.beforeDeadline = true;
    }

    public Loan(User user, Item item, Employee employee){
        this.setLoanDate();
        this.beforeDeadline = true;
        this.setItem(item);
        this.setUser(user);
        this.setEmployee(employee);
    }

    public Loan(User user, Item item){
        this.setLoanDate();
        this.beforeDeadline = true;
        this.setItem(item);
        this.setUser(user);
    }

    public void setLoanDate(){
        SimpleDateFormat formater = new SimpleDateFormat("dd/M/yyyy");
        Date currentDate = new Date();

        loanDate = formater.format(currentDate);
    }

    public void setUser(User user){

        User actualUser = this.user;
        this.user = user;

        if(actualUser != null){
            actualUser.getLoanLine().remove(this);
        }
        if(user != null){
            user.getLoanLine().add(this);
        }
    }

    public void setItem(Item item){

        if(sameAsFormerItem(item)){
            return;
        }

        Item actualItem = this.item;
        this.item = item;

        if(actualItem != null){
            actualItem.setLoan(null);
        }
        if(item != null){
            item.setLoan(this);
        }
    }

    private boolean sameAsFormerItem(Item newItem){
        return item == null ? newItem == null : item.equals(newItem);
    }

}
