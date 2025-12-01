package be.ucll.ui;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLayout;


public class MainLayout extends VerticalLayout implements RouterLayout, AfterNavigationObserver {
    private final Div content;
    private final Button logout;

    public MainLayout() {
        H1 header = new H1("Mijn header");
        header.addClassName("app-header");

        logout = new Button("Logout");
        logout.addClassName("logout-btn");
        header.add(logout);
        logout.addClickListener(e -> {
            // Delegate to Spring Security's logout endpoint so that the session
            // and security context are fully cleared server-side.
            getUI().ifPresent(ui -> ui.getPage().setLocation("logout"));
        });

        content = new Div();
        content.addClassName("app-content");
        content.setSizeFull();


        Footer footer = new Footer();
        footer.addClassName("app-footer");
        footer.add("Mijn footer");

        setSizeFull();
        setPadding(false);
        setMargin(false);
        setSpacing(false);
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
