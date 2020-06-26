package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.SessionTimeout;
import org.springframework.data.repository.CrudRepository;

public interface ISessionTimeoutDao extends CrudRepository <SessionTimeout, Long> {
}
