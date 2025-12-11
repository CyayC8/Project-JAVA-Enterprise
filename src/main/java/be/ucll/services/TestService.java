package be.ucll.services;

import be.ucll.repositories.TestEntity;

import java.util.Collection;

public interface TestService {

    Collection<TestEntity> findAll();
}
