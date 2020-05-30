package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.ForeignCurrency;
import com.yo.minimal.rest.models.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserServices {

    public List<User> findAllUser();

    public Optional<User> getByUserName(String userName);

    public Boolean existsByUserName(String UserName);

    public Boolean existsByEmail(String email);

    public User save(User user);

}
