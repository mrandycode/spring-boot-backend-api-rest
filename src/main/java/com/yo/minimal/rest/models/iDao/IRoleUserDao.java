package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.constants.enums.RoleName;
import com.yo.minimal.rest.models.entity.RoleUser;
import com.yo.minimal.rest.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface IRoleUserDao extends CrudRepository<RoleUser, Long> {
    Optional<RoleUser> findByRoleName(RoleName roleName);
}