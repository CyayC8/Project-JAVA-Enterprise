package be.ucll.ui;

import be.ucll.services.TestService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "test", layout = MainLayout.class)
@PermitAll
public class TestView extends VerticalLayout {

    @Autowired
    private TestService testService;

    public TestView() {

        add(new H1("Test Test Test!"));

        Button button = new Button("Ophalen data");
        button.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(final ClickEvent<Button> buttonClickEvent) {
                testService.findAll().forEach(testEntity -> add(new Div("TestEntity value from database:" + testEntity.getValue() + ":::" + testEntity.getNummer())));
            }
        });

        add(button);
    }
}
