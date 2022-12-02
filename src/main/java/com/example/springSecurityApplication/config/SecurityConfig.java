package com.example.springSecurityApplication.config;

import com.example.springSecurityApplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // Конфигурация Spring Security
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Указываем, на какой url адрес фильтр Spring Security будет отправлять неаутентифицированного пользователя при заходе на защищенную страницу:
        httpSecurity
                // Указываем, что все страницы будут защищены процессом аутентификации:
                .authorizeRequests()
                // Указываем, что страница /admin доступна пользователю с ролью ADMIN:
                .antMatchers("/admin").hasRole("ADMIN")
                // Указыаем, что данные страницы доступны все пользователям:
                .antMatchers("/authentication/login", "/authentication/registration", "/error", "/manuscript", "/img/**", "/manuscript/info/{id}").permitAll()
                // Указываем, что все остальные страницы доступны пользователям с ролью user и admin:
                .anyRequest().hasAnyRole("USER", "ADMIN")

                .and()

                .formLogin().loginPage("/authentication/login")
                // Указываем, на какой url будут отправляться данные с формы аутентификации:
                .loginProcessingUrl("/process_login")
                // Указываем, на какой url необходимо направить пользователя после успешной аутентификации:
                .defaultSuccessUrl("/index", true)
                // Указываем, куда необходимо перейти при неверной аутентификации:
                .failureUrl("/authentication/login?error")

                .and()

                .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication/login");
    }


    // Данный метод позволяет настроить аутентификацию:
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
