package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.constants.enums.RoleName;
import com.yo.minimal.rest.models.entity.RoleUser;
import com.yo.minimal.rest.models.entity.User;
import com.yo.minimal.rest.models.iDao.IRoleUserDao;
import com.yo.minimal.rest.models.iDao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleUserServicesImpl implements IRoleUserServices {

    @Autowired
    private IRoleUserDao iRoleUserDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleUser> findByRolName(RoleName roleName) { return (Optional<RoleUser>) iRoleUserDao.findByRoleName(roleName); }



}
