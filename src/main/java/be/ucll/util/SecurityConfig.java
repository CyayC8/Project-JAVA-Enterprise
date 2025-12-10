package be.ucll.util;

import be.ucll.repositories.UserRepository;
import be.ucll.ui.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        // Load users from the application database
        return username -> userRepository.findByUsername(username).map(u -> User.withUsername(u.getUsername())
                // {noop} = zegt tegen Spring Security dat er geen encoding is gebruikt dus niet proberen decoden of hashen
                .password("{noop}" + u.getPassword()).roles("USER").build()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/images/**", "/icons/**").permitAll()); //allow images and icons

        super.configure(http); //laadt de Vaadin security app = bescherming + acces to route security

        //LOGIN
        setLoginView(http, LoginView.class); //indien niet ingelogd stuur naar Vaadin view = LoginView
        // After successful authentication, redirecten to search
        http.formLogin(form -> form.defaultSuccessUrl("/search", true)); //true forceert ALTIJD naar /search - false redirect terug naar de pagina waar de bezoeker vadndaan kwam zoals /admin of dergelijk


        //LOGOUT
        http.logout(logout -> logout
                // Allow GET for logout so the Vaadin button can simply navigate to /logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //logout via GET (bv druk op knop)
                .clearAuthentication(true) //verwijder user-info uit security context
                .invalidateHttpSession(true) //verwijdert sessie server side
                .deleteCookies("JSESSIONID") //verwijdert cookies uit browser dus nieuwe sessie bij volgende login
                .logoutSuccessUrl("/login") //indien succevol uitgelogd -> login

        );
    }
}