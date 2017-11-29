package matmic.librarymaneger.security;


import matmic.librarymaneger.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/librarian");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("admin");
        return driverManagerDataSource;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(employeeService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource())
                .withDefaultSchema()
                .withUser("email").password("password").roles("USER")
                .and()
                .withUser("email").password("password").roles("USER", "ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.csrf().disable()
                .authorizeRequests()
                   .antMatchers("/login").permitAll()
                   .antMatchers("/registration").permitAll()

                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/employee/**").hasAnyRole("EMPLOYEE")
                    .antMatchers("/resources/**").hasAnyRole()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/index")
                    .permitAll()
                    .and()
                .logout().logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .exceptionHandling().accessDeniedPage("/403");

//        http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();

//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/registration").permitAll()
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN").anyRequest()
//                .authenticated().and().csrf().disable().formLogin()
//                .loginPage("/login").failureUrl("/login?error=true")
//                .defaultSuccessUrl("/index")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/").and().exceptionHandling()
//                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**", "/css/**", "/js/**", "/images/**", ".webjars/**");
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
