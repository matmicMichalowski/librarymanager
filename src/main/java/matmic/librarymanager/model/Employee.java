package matmic.librarymanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymanager.model.rolemodel.EmployeeRole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Employee implements Serializable{
    private static final long serialVersionUID = 902783495L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max=50)
    private String lastName;

    @Pattern(regexp = "(?:\\d{3}-){2}\\d{3}", message = "Please provide your phone number in this pattern '123-123-123'")
    @NotEmpty(message = "Please provide your mobile phone in this way '999-999-999'")
    private String mobile;

    @Column(name = "active")
    private boolean isActive;
    private String resetToken;




    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Loan> loansByEmployee = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<EmployeeRole> employeeRoles = new HashSet<>();


    public void resetPasswordToken(){
        this.resetToken = null;
    }

}
