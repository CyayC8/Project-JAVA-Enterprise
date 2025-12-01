package be.ucll.util;

import be.ucll.ui.LoginView;
import be.ucll.repositories.UserRepository;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
    @Configuration
    public class SecurityConfig extends VaadinWebSecurity {

        @Autowired
        private UserRepository userRepository;

        @Bean
        public UserDetailsService userDetailsService() {
            // Load users from the application database (seeded in InitialDataSetup)
            return username -> userRepository.findByUsername(username)
                    .map(u -> User.withUsername(u.getUsername())
                            // Passwords are stored in plain text in InitialDataSetup, so use {noop}
                            .password("{noop}" + u.getPassword())
                            .roles("USER")
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            setLoginView(http, LoginView.class);
            // After successful authentication, redirect to the main view
            http.formLogin(form -> form.defaultSuccessUrl("/main", true));

            // Configure proper logout handling: invalidate session, clear authentication,
            // delete JSESSIONID cookie and redirect back to the login view.
            http.logout(logout -> logout
                    // Allow GET for logout so the Vaadin button can simply navigate to /logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
            );
        }
    }