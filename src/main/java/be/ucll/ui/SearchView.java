package be.ucll.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
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

@Route(value = "search", layout = MainLayout.class)
@PermitAll
public class SearchView extends VerticalLayout {

    public SearchView() {

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setPadding(true);
        setSpacing(true);

        add(new H1("Search View"));

        //rijen maken

        HorizontalLayout row1= new HorizontalLayout();
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

        row1.add(minBedrag,maxBedrag,aantalProducten);
        row2.add(productNaam,validEmailField,checkbox);
        row2.setVerticalComponentAlignment(Alignment.CENTER, checkbox);
        row3.add(search,delete);
        add(row2,row1,row3);


        //Logica
        delete.addClickListener(e -> {
            minBedrag.clear();
            maxBedrag.clear();
            aantalProducten.clear();
            productNaam.clear();
            validEmailField.clear();
            checkbox.setValue(false);
        });




    }
}
