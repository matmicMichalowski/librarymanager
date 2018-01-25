package matmic.librarymanager.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{



    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource())
                .usersByUsernameQuery(
                        "select email,password,active from employee where email=?")
                .authoritiesByUsernameQuery(
                       "select e.email, r.name from employee as e inner join employee_role as er on(e.id=er.employee_id) inner join role as r on(er.role_id=r.id) where e.email=?");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http
                .authorizeRequests()
                    .antMatchers("/login", "/").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/reset-request").permitAll()
                    .antMatchers("/reset").permitAll()
                    .antMatchers("/400error").permitAll()
                    .antMatchers("/employee/list*").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/dashboard")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                    .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( "/resources/**","/static/**" ,"/css/**", "/js/**", "/images/**", "/webjars/**", "/bootstrap/**");
    }


    @Autowired
    public DriverManagerDataSource dataSource(){
        return new DriverManagerDataSource();
    }

}
