package matmic.librarymanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class User extends ImageSuperclass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastName;

    @Pattern(regexp = "(?:\\d{3}-){2}\\d{3}")
    private String phoneNumber;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String city;

    @NotEmpty
    @Size(min = 15, max = 128)
    private String address;

    @Pattern(regexp = "(?:\\d{2}-)\\d{3}", message = "Wrong post code pattern. Please provide it with this pattern \"NN-NNN\"")
    private String postCode;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Loan> loanLine = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addLoan(Loan loan){
        if(loanLine.contains(loan)){
            return;
        }

        this.loanLine.add(loan);
        loan.setUser(this);
    }



}
