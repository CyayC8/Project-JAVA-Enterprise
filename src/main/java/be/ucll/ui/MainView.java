package be.ucll.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "main" , layout = MainLayout.class)
@PermitAll
public class MainView extends VerticalLayout {
    public MainView() {

        add(new H1("Dit is de dynamische body van de hoofdpagina"));
    }
}