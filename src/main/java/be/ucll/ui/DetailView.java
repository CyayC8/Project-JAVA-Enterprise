package be.ucll.ui;

import be.ucll.repositories.OrderEntity;
import be.ucll.repositories.ProductEntity;
import be.ucll.services.OrderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "detail", layout = MainLayout.class)
@PermitAll
public class DetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private final OrderService orderService;
    private final Grid<ProductEntity> productGrid = new Grid<>(ProductEntity.class, false);

    public DetailView(OrderService orderService) {
        this.orderService = orderService;

        setPadding(true);
        setSpacing(true);

        add(new H1("Detailpagina"));
    }

    @Override
    public void setParameter(BeforeEvent event, Long orderId) {

        OrderEntity order = orderService.findById(orderId);

        if (order == null) {
            add(new H3("Bestelling niet gevonden"));
            return;
        }

        showOrderDetails(order);

        // dynamische productentabel
        productGrid.addColumn(ProductEntity::getProductId).setHeader("ProductId");
        productGrid.addColumn(ProductEntity::getName).setHeader("Naam");
        productGrid.addColumn(ProductEntity::getDescription).setHeader("Beschrijving");
        productGrid.addColumn(ProductEntity::getPrice).setHeader("Prijs (€)");
        productGrid.setItems(order.getProducts());

        add(new H3("Product Details"));
        add(productGrid);

        Button back = new Button("Terug", e -> UI.getCurrent().navigate("search"));
        add(back);
    }

    private void showOrderDetails(OrderEntity order) {

        add(new H3("Bestelling Detail"));

        Span orderId = new Span("BestelId: " + order.getOrderId());
        Span klantNr = new Span("Klantnr: " + order.getUser().getUserId());
        Span aantal = new Span("#producten: " + order.getAantalProducten());
        Span delivered = new Span("Afgeleverd: " + (order.getAfgeleverd() ? "Ja" : "Nee"));
        Span total = new Span("Totaal: €" + order.getTotaalBedrag());

        VerticalLayout table = new VerticalLayout(orderId, klantNr, aantal, delivered, total);
        table.setSpacing(false);
        add(table);
    }
}

