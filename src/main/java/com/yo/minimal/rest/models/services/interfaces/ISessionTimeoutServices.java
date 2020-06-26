package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.SessionTimeout;

import java.util.List;

public interface ISessionTimeoutServices {

    public List<SessionTimeout> findAllSessionTimeout();

    public SessionTimeout findByIdSessionTimeout(Long id);

    public SessionTimeout saveSessionTimeout(SessionTimeout sessionTimeout);
}
