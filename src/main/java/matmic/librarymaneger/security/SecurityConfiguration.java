package matmic.librarymaneger.security;


import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmployeeServiceImpl employeeService;




    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        System.out.println("to ja wywołuję tam");
        auth.userDetailsService(employeeService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource())
                .usersByUsernameQuery(
                        "select email,password,active from employee where username=?")
                .authoritiesByUsernameQuery(
                       "select e.email, r.role from employee e inner join employee_role er on(e.id=er.employee_id) inner join role r on(er.role_id=r.id) where e.email=?");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http
                .authorizeRequests()
                    .antMatchers("/login*").permitAll()
                    .antMatchers("/newemployee*").permitAll()
                    .antMatchers("/registration*").permitAll()
                    .antMatchers("/submit*").permitAll()
                    .antMatchers("/index").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/index")
                    .usernameParameter("email")
                    .passwordParameter("password")
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
//
//                .and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/").and().exceptionHandling()
//                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( "/resources/**","/static/**" ,"/css/**", "/js/**", "/images/**", "/webjars/**");
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    //@Bean(name="dataSource")
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/librarian");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("admin");
        return driverManagerDataSource;
    }
}
