package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRoleCommand {

    private Long employeeRoleId;
    private Long employeeId;
    private Long roleId;
}
