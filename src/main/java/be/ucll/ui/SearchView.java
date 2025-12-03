package be.ucll.ui;


import be.ucll.repositories.OrderEntity;
import be.ucll.services.OrderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import be.ucll.repositories.OrderEntity;


import java.util.Collection;
import java.util.List;

@Route(value = "search", layout = MainLayout.class)
@PermitAll
public class SearchView extends VerticalLayout {

    @Autowired
    private OrderService orderService;

    public SearchView() {

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

        TextField productNaam = new TextField();
        productNaam.setLabel("Product naam");
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
        add(minBedrag);

        NumberField maxBedrag = new NumberField();
        maxBedrag.setLabel("Maximum bedrag");
        Div euroSuffix2 = new Div();
        euroSuffix2.setText("€");
        maxBedrag.setSuffixComponent(euroSuffix2);
        add(maxBedrag);

        IntegerField aantalProducten = new IntegerField();
        aantalProducten.setStepButtonsVisible(true);
        aantalProducten.setMin(0);
        aantalProducten.setLabel("Aantal producten");
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


        //verborgen deel

        Div output = new Div();
        output.setText("Results will appear here...");
        output.getStyle().set("color", "black");
        output.getStyle().set("font-weight", "bold");

        search.addClickListener(e -> {
            Collection<OrderEntity> orders = orderService.findAll();
            createGridBasic((List<OrderEntity>) orders);
        });
        add(output);
        output.setVisible(true);

        SecurityContextHolder.getContext().getAuthentication().getCredentials();


        //Logica

        //delete button clears all fields
        delete.addClickListener(e -> {
            minBedrag.clear();
            maxBedrag.clear();
            aantalProducten.clear();
            productNaam.clear();
            validEmailField.clear();
            checkbox.setValue(false);
        });

    }

    private void createGridBasic(List<OrderEntity> orders) {
        Button mail = new Button("E-Mail");
        mail.setIcon(VaadinIcon.ENVELOPE.create());

        Grid<OrderEntity> grid = new Grid<>(OrderEntity.class, false);

        grid.addColumn(OrderEntity::getOrderId).setHeader("Order ID");
        grid.addColumn(order -> order.getUser().getUserId()).setHeader("User ID");
        grid.addColumn(OrderEntity::getAantalProducten).setHeader("#products");
        grid.addColumn(OrderEntity::getAfgeleverd).setHeader("Afgeleverd");
        grid.addColumn(OrderEntity::getTotaalBedrag).setHeader("Totaal");


        grid.addComponentColumn(order -> {
            Button detailsButton = new Button("Details");
            detailsButton.addClickListener(e -> {
                UI.getCurrent().navigate("/order" + order.getOrderId());
            });
            return detailsButton;
        }).setHeader("Details");

        grid.setItems(orders);
        add(grid);
        add(mail);
    }

}



