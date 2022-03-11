package pe.com.egonzalesd.msspsupportexchangerate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.com.egonzalesd.msspsupportexchangerate.security.JWTAuthorizationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.GET,"/rate/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/rate/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/rate/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
