package be.ucll.ui;

import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "detail/:id", layout = MainLayout.class)
@PermitAll
public class DetailView {
}
