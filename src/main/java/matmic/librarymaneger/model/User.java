package matmic.librarymaneger.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class User extends ImageSuperclass{

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

//    @Lob
//    private Byte[] userImage;


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
