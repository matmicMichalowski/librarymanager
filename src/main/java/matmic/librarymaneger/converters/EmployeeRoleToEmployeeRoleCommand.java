package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.EmployeeRoleCommand;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRoleToEmployeeRoleCommand implements Converter<EmployeeRole, EmployeeRoleCommand> {

    @Synchronized
    @Nullable
    @Override
    public EmployeeRoleCommand convert(EmployeeRole employeeRole) {
        if(employeeRole == null) {
            return null;
        }

        final EmployeeRoleCommand employeeRoleCommand = new EmployeeRoleCommand();
        employeeRoleCommand.setEmployeeId(employeeRole.getEmployee().getId());
        employeeRoleCommand.setEmployeeRoleId(employeeRole.getEmployeeRoleId());
        employeeRoleCommand.setRoleId(employeeRole.getRole().getId());

        return employeeRoleCommand;
    }
}
