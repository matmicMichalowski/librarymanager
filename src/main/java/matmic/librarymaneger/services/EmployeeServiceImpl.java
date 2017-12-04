package matmic.librarymaneger.services;


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


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                               EmployeeRoleRepository employeeRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

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
    public Employee saveEmployee(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));

        if(employeeRepository.findAll().size() < 1){
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("ADMIN")));
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE")));
            employee.setActive(true);
        }else{
            employee.getEmployeeRoles().add(new EmployeeRole(employee, roleRepository.findByName("EMPLOYEE")));
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = findEmployeeByEmail(email);
        if(employee == null){
            return null;
        }
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
}

