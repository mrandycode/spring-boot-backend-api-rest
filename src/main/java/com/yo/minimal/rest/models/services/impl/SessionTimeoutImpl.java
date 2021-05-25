package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.SessionTimeout;
import com.yo.minimal.rest.models.iDao.ISessionTimeoutDao;
import com.yo.minimal.rest.models.services.interfaces.ISessionTimeoutServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionTimeoutImpl implements ISessionTimeoutServices {

    @Autowired
    private ISessionTimeoutDao iSessionTimeoutDao;

    @Override
    @Transactional(readOnly = true)
    public List<SessionTimeout> findAllSessionTimeout() {
        return (List<SessionTimeout>) iSessionTimeoutDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SessionTimeout findByIdSessionTimeout(Long id) {
        return iSessionTimeoutDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public SessionTimeout saveSessionTimeout(SessionTimeout sessionTimeout) {
        return iSessionTimeoutDao.save(sessionTimeout);
    }
}
