package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findAllProductNames() {
        return entityManager.createQuery("SELECT p.name FROM ProductEntity p").getResultList();
    }
}
