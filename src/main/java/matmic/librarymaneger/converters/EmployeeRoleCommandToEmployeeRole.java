package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.EmployeeRoleCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;
import matmic.librarymaneger.model.rolemodel.Role;
import matmic.librarymaneger.repositories.EmployeeRepository;
import matmic.librarymaneger.repositories.RoleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeRoleCommandToEmployeeRole implements Converter<EmployeeRoleCommand, EmployeeRole> {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    public EmployeeRoleCommandToEmployeeRole(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public EmployeeRole convert(EmployeeRoleCommand employeeRoleCommand) {
        if(employeeRoleCommand == null) {
            return null;
        }

        final EmployeeRole employeeRole = new EmployeeRole();
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeRoleCommand.getEmployeeId());
        if(employeeOptional.isPresent()){
            employeeRole.setEmployee(employeeOptional.get());
        }
        employeeRole.setEmployeeRoleId(employeeRoleCommand.getEmployeeRoleId());
        Optional<Role> roleOptional = roleRepository.findById(employeeRoleCommand.getRoleId());
        if(roleOptional.isPresent()){
            employeeRole.setRole(roleOptional.get());
        }


        return employeeRole;
    }
}
