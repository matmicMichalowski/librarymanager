package matmic.librarymaneger.services;


import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.converter.EmployeeCommandToEmployee;
import matmic.librarymaneger.converter.EmployeeToEmployeeCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;
import matmic.librarymaneger.repositories.EmployeeRepository;
import matmic.librarymaneger.repositories.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService{

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeToEmployeeCommand employeeToEmployeeCommand;
    private final EmployeeCommandToEmployee employeeCommandToEmployee;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder, EmployeeToEmployeeCommand employeeToEmployeeCommand, EmployeeCommandToEmployee employeeCommandToEmployee) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.employeeToEmployeeCommand = employeeToEmployeeCommand;
        this.employeeCommandToEmployee = employeeCommandToEmployee;
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public EmployeeCommand findEmployeeCommandById(Long id) {
        return employeeToEmployeeCommand.convert(employeeRepository.findById(id).get());
    }

    @Override
    public Set<Employee> getEmployees(){
        Set<Employee> employees = new HashSet<>();

        employeeRepository.findAll().iterator().forEachRemaining(employee -> {
            if(employee.getEmployeeRoles().size() < 2){
                employees.add(employee);
            }
        });

        return employees;
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));

        if(employeeRepository.findAll().size() < 1){
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("ADMIN")));
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE")));
            employee.setActive(true);
        }else{
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE")));
        }
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    @Transactional
    public EmployeeCommand updateEmployee(EmployeeCommand employeeCommand) {


        Employee updatedEmployee = employeeCommandToEmployee.convert(employeeCommand);
        employeeRepository.save(updatedEmployee);

        return employeeToEmployeeCommand.convert(updatedEmployee);
    }

    @Override
    public Optional<Employee> findEmployeeByResetToken(String resetToken) {
        return employeeRepository.findByResetToken(resetToken);
    }

//    @Override
//    @Transactional
//    public EmployeeCommand updateEmployee(EmployeeCommand employeeCommand){
//
//    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = findEmployeeByEmail(email);


        if(employee == null){
            EmployeeRole role = new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE"));
            Set<EmployeeRole> roleSet = new HashSet<>();
            roleSet.add(role);
            return new User(" ", " ", true, true, true, true,
                    getUserAuthority(roleSet));
        }
        return new User(employee.getEmail(), employee.getPassword(), employee.isActive(), true, true, true,
                getUserAuthority(employee.getEmployeeRoles()));
    }

    private List<GrantedAuthority> getUserAuthority(Set<EmployeeRole> employeeRoles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (EmployeeRole role : employeeRoles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
        }


        return authorities;
    }


    @Override
    @Transactional
    public  void switchEmployeeStatus(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()){
           Employee employee1 = employee.get();
           if(employee1.isActive()){
               employee1.setActive(false);
           }else{
               employee1.setActive(true);
           }
           employeeRepository.save(employee1);
        }
    }

    @Override
    public void deleteEmployee(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            employeeRepository.delete(employee.get());
        }
    }

}

