package dev.webservice_admin.config;

import dev.webservice_admin.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()

                //Các trang không yêu cầu login
                .antMatchers("/login", "/logout").permitAll()

                //Các static không cần login
                .antMatchers("/static/**").permitAll()

                //Trang thuộc về role
                .antMatchers("/order/**", "/createOrder/**", "/product", "/type-product", "/introduce", "/dashboard")
                .access("hasRole('ROLE_STAFF_SALES')")

                .antMatchers("/profile", "/")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF_SALES')")

                .antMatchers("/nhan-vien")
                .access("hasRole('ROLE_ADMIN')")

                .antMatchers("/khach-hang")
                .access("hasAnyRole('ROLE_STAFF_SALES', 'ROLE_ADMIN')")

                //Trang không đúng role sẽ bắt lỗi
                .and()
                .exceptionHandling().accessDeniedPage("/403")

                //Cấu hình trang login
                .and()
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()

                .logoutUrl("/logout").logoutSuccessUrl("/login");
    }
}
