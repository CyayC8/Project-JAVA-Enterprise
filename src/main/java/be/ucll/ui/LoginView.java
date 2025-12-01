package be.ucll.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "login" , layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends Div {

    private final LoginForm loginForm;

    public LoginView() {
        // See login-rich-content.css
        addClassName("login-rich-content");

        loginForm = new LoginForm();
        // Delegate authentication to Spring Security (Vaadin security integration)
        // The processing URL is "/login"; LoginForm accepts it without the leading slash too.
        loginForm.setAction("login");
        // Inherit the application theme (modern) colors and typography
        add(loginForm);
        
    }

//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        // If Spring Security redirected here after a failed login attempt,
//        // it appends ?error to the URL. When present, show the error on the form.
//        boolean hasError = event.getLocation().getQueryParameters()
//                .getParameters()
//                .containsKey("error");
//        loginForm.setError(hasError);
//    }

}

