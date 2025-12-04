package be.ucll.repositories;

import java.util.List;

public interface ProductRepository {

    List<String> findAllProductNames();
}
