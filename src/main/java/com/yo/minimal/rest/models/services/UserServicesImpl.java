package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.ForeignCurrency;
import com.yo.minimal.rest.models.entity.User;
import com.yo.minimal.rest.models.iDao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements IUserServices {

    @Autowired
    private IUserDao iUserDao;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUser() { return (List<User>) iUserDao.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByUserName (String userName) {
        return iUserDao.findByUserName(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByUserName (String userName) { return iUserDao.existsByUserName (userName); }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail (String email) { return iUserDao.existsByEmail (email); }

    @Override
    public User save (User user) { return iUserDao.save (user); }

}
