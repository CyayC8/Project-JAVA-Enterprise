package be.ucll.services;

import java.util.Collection;

import be.ucll.repositories.TestEntity;

public interface TestService {

	Collection<TestEntity> findAll();
}
