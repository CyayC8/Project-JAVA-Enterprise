package be.ucll.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "detail/:id", layout = MainLayout.class)
@PermitAll
public class DetailView extends VerticalLayout {
    public DetailView() {

        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setPadding(true);
        setSpacing(true);

        add(new H1("Details"));

        H3 bestellingDetails = new H3();
        bestellingDetails.getStyle().set("text-decoration", "underline");
        bestellingDetails.setText("Bestelling Details");
        add(bestellingDetails);

    }
}
