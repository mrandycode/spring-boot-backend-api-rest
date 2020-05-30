package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.constants.enums.RoleName;
import com.yo.minimal.rest.models.entity.RoleUser;
import com.yo.minimal.rest.models.entity.User;

import java.util.List;
import java.util.Optional;


public interface IRoleUserServices {

    public Optional<RoleUser> findByRolName(RoleName roleName);

}
