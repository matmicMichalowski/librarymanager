package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.EmployeeCommand;
import matmic.librarymaneger.converters.EmployeeCommandToEmployee;
import matmic.librarymaneger.converters.EmployeeToEmployeeCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;
import matmic.librarymaneger.repositories.EmployeeRepository;
import matmic.librarymaneger.repositories.EmployeeRoleRepository;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService{

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeCommandToEmployee employeeCommandToEmployee;
    private final EmployeeToEmployeeCommand employeeToEmployeeCommand;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                               EmployeeRoleRepository employeeRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmployeeCommandToEmployee employeeCommandToEmployee, EmployeeToEmployeeCommand employeeToEmployeeCommand) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.employeeCommandToEmployee = employeeCommandToEmployee;
        this.employeeToEmployeeCommand = employeeToEmployeeCommand;
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public Set<Employee> getEmployees(){
        Set<Employee> employees = new HashSet<>();

        employeeRepository.findAll().iterator().forEachRemaining(employees::add);
        return employees;
    }

    @Override
    @Transactional
    public EmployeeCommand saveEmployee(EmployeeCommand employeeCommand) {
        System.out.println("service?");
        employeeCommand.setPassword(bCryptPasswordEncoder.encode(employeeCommand.getPassword()));
        Employee employee = employeeCommandToEmployee.convert(employeeCommand);


        if(employeeRepository.findAll().size() < 1){
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("ADMIN")));
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE")));
            employee.setActive(true);
        }else{
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE")));
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeToEmployeeCommand.convert(savedEmployee);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        Employee employee = findEmployeeByEmail(email);
        if(employee == null){
            System.out.println(email);
            return null;
        }
        System.out.println();
        List<GrantedAuthority> authorities = getUserAuthority(employee.getEmployeeRoles());
        return buildUserForAuthentication(employee, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<EmployeeRole> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (EmployeeRole role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole().getName()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(Employee employee, List<GrantedAuthority> authorities) {
        return new User(employee.getEmail(), employee.getPassword(), employee.isActive(), true, true, true, authorities);
    }
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String employeeEmail) throws UsernameNotFoundException {
//        Employee employee = employeeRepository.findByEmail(employeeEmail);
//        if (employee == null){
//            System.out.println("null " + employeeEmail);
//        }
//        Set<GrantedAuthority> authorities = new HashSet<>();
//
//        for (EmployeeRole role: employee.getEmployeeRoles()){
//            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
//        }
//        return new org.springframework.security.core.userdetails
//                .User(employee.getEmail(), employee.getPassword(), authorities);
//    }

//    private List<GrantedAuthority> getEmployeeAuthority(Set<EmployeeRole> userRoles) {
//        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
//        for (EmployeeRole role : userRoles) {
//            roles.add(new SimpleGrantedAuthority(role.getRole().getName()));
//        }
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
//        return grantedAuthorities;
//    }
//
//    private UserDetails buildEmployeeForAuthentication(Employee employee, List<GrantedAuthority> authorities){
//        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(),
//                authorities);
//    }
}

