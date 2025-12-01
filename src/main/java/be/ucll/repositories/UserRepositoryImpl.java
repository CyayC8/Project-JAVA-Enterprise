package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> findAll() {

        return entityManager.createQuery("from UserEntity").getResultList();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        List<UserEntity> result = entityManager
                .createQuery("from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList();
        return result.stream().findFirst();
    }
}