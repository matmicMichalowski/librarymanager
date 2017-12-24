package matmic.librarymanager.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;


@Entity
@Setter
@Getter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate loanDate;
    private LocalDate loanDeadline;
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

        Long loanDays = 14L;

        LocalDate currentDate = LocalDate.now();

        LocalDate deadlineDate = currentDate.plusDays(loanDays);


        if (deadlineDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            deadlineDate = deadlineDate.plusDays( 1);
        }else if( deadlineDate.getDayOfWeek() == DayOfWeek.SATURDAY){
            deadlineDate = deadlineDate.minusDays(1);
        }


        loanDeadline = deadlineDate;
        loanDate = currentDate;
    }

    public void setUser(User user){

        if(sameAsFormerUser(user)){
            return;
        }

        User actualUser = this.user;
        this.user = user;

        if(actualUser != null){
            actualUser.getLoanLine().remove(this);
        }
        if(user != null){
            user.getLoanLine().add(this);
        }
    }

    private boolean sameAsFormerUser(User newUser){
        return user == null ? newUser == null : user.equals(newUser);
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

    public void setEmployee(Employee employee){

        if(sameAsFormerEmployee(employee)){
            return;
        }

        Employee actualEmployee = this.employee;
        this.employee = employee;

        if(actualEmployee != null){
            actualEmployee.getLoansByEmployee().remove(this);
        }
        if(employee != null){
            employee.getLoansByEmployee().add(this);
        }
    }

    private boolean sameAsFormerEmployee(Employee newEmployee){
        return employee == null ? newEmployee == null : employee.equals(newEmployee);
    }
}
