package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface IUserDao extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String nameUser);
    boolean existsByUserName(String nameUser);
    boolean existsByEmail(String email);
}