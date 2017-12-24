package matmic.librarymanager.model.rolemodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymanager.model.Employee;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class EmployeeRole {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeRoleId;

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
