package matmic.librarymanager.security;


import matmic.librarymanager.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class MyAppConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Lazy
    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{

        auth.userDetailsService(employeeService)
                .passwordEncoder(passwordEncoder());
    }
}
