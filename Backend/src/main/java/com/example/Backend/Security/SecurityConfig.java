package com.example.Backend.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF using the new method
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated() // All requests must be authenticated
                )
                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }

    // ... rest of your configuration



    // No @Bean annotation here
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
              .ldapAuthentication()
                .userDnPatterns("cn={0},ou=users,ou=system")
                .groupSearchBase("ou=groups,ou=system")
                .contextSource()
                .url("ldap://localhost:10389");
    }
   
    
    
    

    @Configuration class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // You can restrict paths with specific ones
                    .allowedOrigins("http://localhost:4200") // Replace with your frontend's URL
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }
}