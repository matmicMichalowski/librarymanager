package matmic.librarymaneger.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class User {

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

    @OneToOne(cascade = CascadeType.ALL)
    private LoanLine userLoan;


    public User() {
        this.userLoan = new LoanLine(this);
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userLoan = new LoanLine(this);
    }
}
