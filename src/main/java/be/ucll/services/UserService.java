package be.ucll.services;

import be.ucll.repositories.UserEntity;

import java.util.Collection;

public interface UserService {

    Collection<UserEntity> findAll();

}
