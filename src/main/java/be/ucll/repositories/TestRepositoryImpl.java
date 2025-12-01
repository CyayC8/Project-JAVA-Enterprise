package be.ucll.repositories;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TestRepositoryImpl implements TestRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Collection<TestEntity> findAll() {

        return entityManager.createQuery("from TestEntity").getResultList();
	}
}
