package be.ucll.ui;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private final LoginForm loginForm;

    public LoginView() {

        addClassName("login");

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);


        loginForm = new LoginForm();
        // Delegate authentication to Spring Security (Vaadin security integration)
        loginForm.setAction("login");
        add(loginForm);

    }

}

