package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<OrderEntity> findAll() {
        return entityManager.createQuery("from OrderEntity").getResultList();
    }

    @Override
    public Collection<OrderEntity> findAllByUserId(Long userId) {
        return entityManager.createQuery("from OrderEntity where user.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }
}
