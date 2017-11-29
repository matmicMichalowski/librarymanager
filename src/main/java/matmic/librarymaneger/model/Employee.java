package matmic.librarymaneger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @Column(name="employee_id")
    private Long id;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @NotBlank
    //@Size(min=8, max=25)
    private String password;

    @NotBlank
    @Size(min=3, max=20)
    private String firstName;

    @NotBlank
    @Size(min=3, max=30)
    private String lastName;

    private boolean isActive;

    @OneToMany(mappedBy = "employee", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<EmployeeRole> employeeRoles = new HashSet<>();


}
