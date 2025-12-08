package be.ucll.ui;


import be.ucll.jms.OrderEmailDTO;
import be.ucll.repositories.*;
import be.ucll.services.EmailService;
import be.ucll.services.OrderService;
import be.ucll.services.UserService;
import be.ucll.services.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import be.ucll.repositories.OrderEntity;


import java.math.BigDecimal;
import java.util.*;

@Route(value = "search", layout = MainLayout.class)
@PermitAll
public class SearchView extends VerticalLayout {

    private final Grid<OrderEntity> grid = new Grid<>(OrderEntity.class, false);
    private final Button mailButton = new Button("E-Mail", VaadinIcon.ENVELOPE.create());
    private final VerticalLayout resultsContainer = new VerticalLayout();

    public SearchView(@Autowired OrderService orderService, @Autowired UserService userService, @Autowired ProductService productService) {

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setPadding(true);
        setSpacing(true);

        add(new H1("Search View"));

        //rijen maken

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setJustifyContentMode(JustifyContentMode.CENTER);
        row1.setSpacing(true);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(JustifyContentMode.CENTER);
        row2.setSpacing(true);

        HorizontalLayout row3 = new HorizontalLayout();
        row3.setWidthFull();
        row3.setJustifyContentMode(JustifyContentMode.CENTER);
        row3.setSpacing(true);

        ComboBox<String> productNaam = new ComboBox<>();
        productNaam.setLabel("Product naam");
        List<String>allProductNames = productService.findAllProductNames();
        productNaam.setItems(allProductNames);
        productNaam.setPlaceholder("Typ om te zoeken...");
        productNaam.setAllowCustomValue(false);
        productNaam.setClearButtonVisible(true);
        add(productNaam);

        EmailField validEmailField = new EmailField();
        validEmailField.setLabel("Email address");
        validEmailField.getElement().setAttribute("email", "email");
        validEmailField.setErrorMessage("Enter a valid email address");
        validEmailField.setClearButtonVisible(true);

        NumberField minBedrag = new NumberField();
        minBedrag.setLabel("Minimum bedrag");
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        minBedrag.setSuffixComponent(euroSuffix);
        minBedrag.setMin(0);
        minBedrag.setErrorMessage("Minimum bedrag moet groter zijn dan 0");
        add(minBedrag);

        NumberField maxBedrag = new NumberField();
        maxBedrag.setLabel("Maximum bedrag");
        Div euroSuffix2 = new Div();
        euroSuffix2.setText("€");
        maxBedrag.setSuffixComponent(euroSuffix2);
        maxBedrag.setMin(0);
        maxBedrag.setErrorMessage("Maximum bedrag moet groter zijn dan 0");
        add(maxBedrag);

        IntegerField aantalProducten = new IntegerField();
        aantalProducten.setStepButtonsVisible(true);
        aantalProducten.setMin(0);
        aantalProducten.setLabel("Aantal producten");
        aantalProducten.setErrorMessage("Voer een geheel getal in");
        add(aantalProducten);

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Afgeleverd");
        add(checkbox);

        Button search = new Button("Search");
        search.setIcon(VaadinIcon.SEARCH.create());
        add(search);

        Button delete = new Button("Delete");
        delete.setIcon(VaadinIcon.TRASH.create());
        add(delete);

        row1.add(minBedrag, maxBedrag, aantalProducten);
        row2.add(productNaam, validEmailField, checkbox);
        row2.setVerticalComponentAlignment(Alignment.CENTER, checkbox);
        row3.add(search, delete);
        add(row2, row1, row3);


        resultsContainer.setWidthFull();
        resultsContainer.setSpacing(true);
        add(resultsContainer);

        setupGrid();


        //Logica

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userService.findByUsername(username);
        Long userId = currentUser != null ? currentUser.getUserId() : null;

        mailButton.addClickListener(e -> {
            //alle relevante variablen er eers tuiothalen dan pas naar de queue zenden of het object meegevem

            List<OrderEntity> currentOrders = grid.getListDataView().getItems().toList();
            if (currentOrders.isEmpty()) {
                Notification.show("Er zijn geen bestellingen om te mailen.", 4000, Notification.Position.TOP_CENTER);
            }

            //Variabelen omzetten naar Data Transfer Object voor veilig en proper serializeren
            List<OrderEmailDTO> ordersToSend = currentOrders.stream().map(order -> new OrderEmailDTO(
                    order.getOrderId(),
                    order.getUser().getUsername(),
                    order.getUser().getEmail(),
                    order.getAantalProducten(),
                    order.getTotaalBedrag(),
                    order.getAfgeleverd()
            )).toList();

            EmailService.

        });


        //verborgen deel


        Div output = new Div();
        output.setText("Results will appear here...");
        output.getStyle().set("color", "black");
        output.getStyle().set("font-weight", "bold");

        search.addClickListener(e -> {
            output.setVisible(false);

            boolean noCriteria = minBedrag.isEmpty()
                    && maxBedrag.isEmpty()
                    && aantalProducten.isEmpty()
                    && (productNaam.getValue() == null || productNaam.getValue().isEmpty())
                    && (validEmailField.getValue() == null || validEmailField.getValue().isEmpty())
                    && (checkbox.getValue() == null || !checkbox.getValue());

            if (noCriteria) {
                output.setText("Geef ten minste één zoekcriteria op");
                output.setVisible(true);
                updateResults(Collections.emptyList());
                return;
            }

            BigDecimal minAmount = minBedrag.getValue() != null ? BigDecimal.valueOf(minBedrag.getValue()) : null;
            // als er een invoer is, omzetten naar BigDecimal (geld) en opslaan in minAmount - indien geen invoer = null
            BigDecimal maxAmount = maxBedrag.getValue() != null ? BigDecimal.valueOf(maxBedrag.getValue()) : null;
            Integer amountOfProducts = aantalProducten.getValue() != null ? aantalProducten.getValue() : null;
            String productName = productNaam.getValue() != null ? productNaam.getValue() : null;
            String email = validEmailField.getValue() != null ? validEmailField.getValue() : null;
            Boolean delivered = checkbox.getValue() != null ? checkbox.getValue() : null;


            List<OrderEntity> filteredOrders = orderService.searchOrders(userId, minAmount, maxAmount, amountOfProducts, productName, email, delivered);


            updateResults(filteredOrders);

        });
        add(output);


        //delete button cleears all fields en resets the page
        delete.addClickListener(e -> {
            minBedrag.clear();
            maxBedrag.clear();
            aantalProducten.clear();
            productNaam.clear();
            validEmailField.clear();
            checkbox.setValue(false);
            updateResults(Collections.emptyList());
        });

    }

    private void setupGrid() {
        grid.addColumn(OrderEntity::getOrderId).setHeader("Order ID");
        grid.addColumn(order -> order.getUser().getUserId()).setHeader("User ID");
        grid.addColumn(OrderEntity::getAantalProducten).setHeader("#products");
        grid.addColumn(new ComponentRenderer<>(order -> {
            if (order.getAfgeleverd() != null && order.getAfgeleverd()) {
                return new Icon(VaadinIcon.CHECK);
            } else {
                Icon cross = new Icon(VaadinIcon.CLOSE);
                cross.setColor("red");
                return cross;
            }
        })).setHeader("Afgeleverd");
        grid.addColumn(OrderEntity::getTotaalBedrag).setHeader("Totaal");


        grid.addComponentColumn(order -> {
            Button detailsButton = new Button("Details");
            detailsButton.addClickListener(e -> {
                UI.getCurrent().navigate("/detail/" + order.getOrderId());
            });
            return detailsButton;
        }).setHeader("Details");
    }

    private void updateResults(List<OrderEntity> orders) {
        resultsContainer.removeAll();
        if (orders.isEmpty()) {
            return;
        }
        grid.setItems(orders);
        resultsContainer.add(grid, mailButton);

    }


}



