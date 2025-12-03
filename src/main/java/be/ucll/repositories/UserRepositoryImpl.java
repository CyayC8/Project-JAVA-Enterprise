package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext //zegt tegen Spring om hier de entitymanager te injecteren
    private EntityManager entityManager;

    @Override
    public List<UserEntity> findAll() {

        return entityManager.createQuery("from UserEntity").getResultList();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        List<UserEntity> result = entityManager
                .createQuery("from UserEntity u where (u.username) = (:username)", UserEntity.class)
                .setParameter("username", username)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}