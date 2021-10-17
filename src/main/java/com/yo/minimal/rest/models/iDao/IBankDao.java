package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Bank;
import org.springframework.data.repository.CrudRepository;

public interface IBankDao extends CrudRepository<Bank, Long> {
}
