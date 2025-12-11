package be.ucll.repositories;

import java.util.Collection;

public interface TestRepository {

    Collection<TestEntity> findAll();
}
