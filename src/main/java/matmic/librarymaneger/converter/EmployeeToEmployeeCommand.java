package matmic.librarymaneger.converter;


import lombok.Synchronized;
import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeCommand implements Converter<Employee, EmployeeCommand> {


    @Synchronized
    @Nullable
    @Override
    public EmployeeCommand convert(Employee employee) {
        if(employee == null) {
            return null;
        }

        final EmployeeCommand employeeCommand = new EmployeeCommand();

        employeeCommand.setId(employee.getId());
        employeeCommand.setFirstName(employee.getFirstName());
        employeeCommand.setLastName(employee.getLastName());
        employeeCommand.setEmail(employee.getEmail());
        employeeCommand.setMobile(employee.getMobile());

        return employeeCommand;
    }
}
