package matmic.librarymaneger.converter;

import lombok.Synchronized;
import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.repositories.EmployeeRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class EmployeeCommandToEmployee implements Converter<EmployeeCommand, Employee>{

    private final EmployeeRepository employeeRepository;

    public EmployeeCommandToEmployee(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Employee convert(EmployeeCommand employeeCommand) {
        if(employeeCommand == null){
            return null;
        }

        Optional<Employee> employeeOptional = employeeRepository.findEmployeeById(employeeCommand.getId());

        Employee employee;


        if(employeeOptional.isPresent()){
            employee = employeeOptional.get();
        }else{
            employee = new Employee();
        }

        employee.setId(employeeCommand.getId());
        employee.setFirstName(employeeCommand.getFirstName());
        employee.setLastName(employeeCommand.getLastName());
        employee.setEmail(employeeCommand.getEmail());
        employee.setMobile(employeeCommand.getMobile());


        return employee;

    }
}
