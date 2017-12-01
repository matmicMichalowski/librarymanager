package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeCommand implements Converter<Employee, EmployeeCommand> {

    private final EmployeeRoleToEmployeeRoleCommand employeeRoleToCmd;

    public EmployeeToEmployeeCommand(EmployeeRoleToEmployeeRoleCommand employeeRoleToCmd) {
        this.employeeRoleToCmd = employeeRoleToCmd;
    }

    @Synchronized
    @Nullable
    @Override
    public EmployeeCommand convert(Employee employee) {
        if(employee == null){
            return null;
        }

        final EmployeeCommand employeeCommand = new EmployeeCommand();
        employeeCommand.setEmail(employee.getEmail());
        employeeCommand.setFirstName(employee.getFirstName());
        employeeCommand.setLastName(employee.getLastName());
        employeeCommand.setPassword(employee.getPassword());
        employeeCommand.setId(employee.getId());
        employee.getEmployeeRoles().forEach(role -> employeeCommand.getEmployeeRoles()
                .add(employeeRoleToCmd.convert(role)));

        return employeeCommand;
    }
}
