package be.ucll.repositories;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Collection<UserEntity> findAll();

    Optional<UserEntity> findByUsername(String username);

}
