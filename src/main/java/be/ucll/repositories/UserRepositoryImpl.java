package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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
                .createQuery("from UserEntity u where lower(u.username) = lower(:username)", UserEntity.class)
                .setParameter("username", username)
                .getResultList();
        return result.stream().findFirst();
    }
}