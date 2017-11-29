package matmic.librarymaneger.model.rolemodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.Employee;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class EmployeeRole implements Serializable {
    private static final long serialVersionUID = 890345L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public EmployeeRole(Employee employee, Role role){
        this.employee = employee;
        this.role = role;
    }

}
