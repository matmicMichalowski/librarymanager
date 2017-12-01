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
    //@Column(name="employee_id")
    private Long id;

    //@Column(name="email")
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;

    @OneToMany(mappedBy = "employee", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
    private Set<EmployeeRole> employeeRoles = new HashSet<>();


}
