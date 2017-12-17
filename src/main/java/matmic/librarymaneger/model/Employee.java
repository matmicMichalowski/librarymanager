package matmic.librarymaneger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;

import javax.persistence.*;
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


    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
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
