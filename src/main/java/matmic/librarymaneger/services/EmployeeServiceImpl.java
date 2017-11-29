package matmic.librarymaneger.services;

import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.model.rolemodel.EmployeeRole;
import matmic.librarymaneger.repositories.EmployeeRepository;
import matmic.librarymaneger.repositories.EmployeeRoleRepository;
import matmic.librarymaneger.repositories.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    @Transactional
    public void saveEmployee(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setRole(roleRepository.findByName("EMPLOYEE"));
        employeeRole.setEmployee(employee);
        employee.getEmployeeRoles().add(employeeRole);
        employeeRepository.save(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String employeeEmail) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        System.out.println(employee.getEmail());
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (EmployeeRole role: employee.getEmployeeRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
        }
        return new org.springframework.security.core.userdetails
                .User(employee.getEmail(), employee.getPassword(), authorities);
    }

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

