package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCommandToEmployee implements Converter<EmployeeCommand, Employee>{

    private final EmployeeRoleCommandToEmployeeRole cmdToEmployeeRole;

    public EmployeeCommandToEmployee(EmployeeRoleCommandToEmployeeRole cmdToEmployeeRole) {
        this.cmdToEmployeeRole = cmdToEmployeeRole;
    }


    @Synchronized
    @Nullable
    @Override
    public Employee convert(EmployeeCommand employeeCommand) {
        if(employeeCommand == null){
            return null;
        }

        final Employee employee = new Employee();
        employee.setEmail(employeeCommand.getEmail());
        employee.setFirstName(employeeCommand.getFirstName());
        employee.setLastName(employeeCommand.getLastName());
        employee.setPassword(employeeCommand.getPassword());
        employee.setId(employeeCommand.getId());
        employeeCommand.getEmployeeRoles().forEach(role -> employee.getEmployeeRoles()
                .add(cmdToEmployeeRole.convert(role)));

        return employee;
    }
}
