package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleCommand {


    private Long id;
    private String name;
    private Set<EmployeeRoleCommand> employeeRoles = new HashSet<>();
}
