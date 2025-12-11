package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TestRepositoryImpl implements TestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<TestEntity> findAll() {

        return entityManager.createQuery("from TestEntity").getResultList();
    }
}
