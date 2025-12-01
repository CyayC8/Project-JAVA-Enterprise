package be.ucll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.spring.VaadinServletContextInitializer;
import com.vaadin.flow.theme.Theme;

@SpringBootApplication
public class SpringMain extends SpringBootServletInitializer implements AppShellConfigurator {

    @Bean
    public VaadinServletContextInitializer vaadinServletContextInitializer(ApplicationContext applicationContext) {
        return new VaadinServletContextInitializer(applicationContext);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }

    // ✅ This is the key for WildFly WAR deployment
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringMain.class);
    }
}
