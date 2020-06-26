package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.constants.enums.RoleName;
import com.yo.minimal.rest.models.entity.RoleUser;

import java.util.Optional;


public interface IRoleUserServices {

    public Optional<RoleUser> findByRolName(RoleName roleName);

}
