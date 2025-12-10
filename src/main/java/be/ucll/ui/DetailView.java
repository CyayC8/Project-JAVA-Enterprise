package be.ucll.ui;

import be.ucll.repositories.OrderEntity;
import be.ucll.repositories.ProductEntity;
import be.ucll.services.OrderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
    private final VerticalLayout wrapper;

    public DetailView(OrderService orderService) {
        this.orderService = orderService;
        setSizeFull();

        wrapper = new VerticalLayout();
        wrapper.setSizeFull();
        wrapper.setPadding(true);
        wrapper.setSpacing(true);
        wrapper.setAlignItems(Alignment.START);

        add(wrapper);
    }

    @Override
    public void setParameter(BeforeEvent event, Long orderId) {

        wrapper.removeAll();

        OrderEntity order = orderService.findById(orderId);
        if (order == null) {
            add(new H3("Bestelling niet gevonden"));
            return;
        }

        H1 title = new H1("Detailpagina");
        title.getStyle().set("margin-bottom", "30px");
        wrapper.add(title);

        showOrderDetails(order);

        H3 productTitle = new H3("Product Details");
        productTitle.getStyle().set("margin-top", "15px");
        wrapper.add(productTitle);

        // dynamische productentabel
        productGrid.addColumn(ProductEntity::getProductId).setHeader("ProductId").setFlexGrow(0);
        productGrid.addColumn(ProductEntity::getName).setHeader("Naam").setFlexGrow(1);
        productGrid.addColumn(ProductEntity::getDescription).setHeader("Beschrijving").setFlexGrow(2);
        productGrid.addColumn(ProductEntity::getPrice).setHeader("Prijs (€)").setFlexGrow(0);
        productGrid.setItems(order.getProducts());
        productGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        productGrid.setSizeFull();

        wrapper.add(productGrid);
        wrapper.setFlexGrow(1, productGrid);

        Button back = new Button("Terug", e -> UI.getCurrent().navigate("search"));
        wrapper.add(back);

    }


    private void showOrderDetails(OrderEntity order) {

        FormLayout form = new FormLayout();
        Span orderId = new Span("BestelId: " + order.getOrderId());
        Span klantNr = new Span("Klantnr: " + order.getUser().getUserId());
        Span aantal = new Span("#producten: " + order.getAantalProducten());
        Span delivered = new Span("Afgeleverd: " + (order.getAfgeleverd() ? "Ja" : "Nee"));
        Span total = new Span("Totaal: €" + order.getTotaalBedrag());
        form.getStyle().set("column-gap", "10px");
        form.getStyle().set("row-gap", "20px");
        form.add(orderId, klantNr, aantal, delivered, total);


        wrapper.add(new H3("Bestelling Detail"));
        wrapper.add(form);
    }
}

