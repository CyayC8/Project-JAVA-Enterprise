package be.ucll.ui;

import be.ucll.repositories.UserRepository;
import be.ucll.services.OrderService;
import be.ucll.services.UserService;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.beans.factory.annotation.Autowired;


public class MainLayout extends VerticalLayout implements RouterLayout, AfterNavigationObserver {
    private final Div content;
    private final Button logout;

    public MainLayout() {
        H1 header = new H1("Cedric's Pokéshop");
        header.addClassName("app-header");
        header.getStyle().set("width", "100%");
        header.getStyle().set("background-color", "#FFCB05");
        header.getStyle().set("color", "#3B4CCA");
        header.getStyle().set("font-weight", "bold");
        header.getStyle().set("font-size", "2em");
        header.getStyle().set("text-align", "center");
        header.getStyle().set("box-shadow", "0 4px 6px rgba(0,0,0,0.1)");
        header.getStyle().set("border-bottom", "4px solid #3B4CCA");

        Span pokeball = new Span(VaadinIcon.CIRCLE.create());
        pokeball.getStyle().set("color", "#FF0000");
        pokeball.getStyle().set("margin-right", "10px");
        header.addComponentAsFirst(pokeball);

        logout = new Button("Logout");
        logout.setIcon(VaadinIcon.SIGN_OUT.create());
        logout.addClassName("logout-btn");
        header.add(logout);
        logout.addClickListener(e -> {
            // Laat Spring Security's logout endpoint dit doen zodat sessie en security context cleared worden server-side
            getUI().ifPresent(ui -> ui.getPage().setLocation("logout"));
        });

        content = new Div();
        content.addClassName("app-content");
        content.setSizeFull();


        Footer footer = new Footer();
        footer.addClassName("app-footer");

        footer.getStyle().set("display", "flex");
        footer.getStyle().set("justify-content", "center");
        footer.getStyle().set("align-items", "center");
        footer.getStyle().set("padding", "10px");
        footer.getStyle().set("font-size", "0.9em");
        footer.getStyle().set("color", "#888");

        footer.add(VaadinIcon.COFFEE.create(),
                new Span("Built by Cedric Swalens © 2025"));


        setSizeFull();
        setPadding(false);
        setMargin(false);
        setSpacing(false);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(header, content, footer);
        expand(content);
    }

    @Override
    public void showRouterLayoutContent(HasElement viewContent) {
        content.getElement().appendChild(viewContent.getElement());
    }
    //Zolang het dynamische deel een Div of Verticallayout is dan werkt showRouterLayoutCOntent via HasElement

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Hide logout button on the login route, show it elsewhere
        String path = event.getLocation().getPath();
        boolean onLogin = "login".equals(path) || path == null || path.isEmpty();
        logout.setVisible(!onLogin);
    }
}
