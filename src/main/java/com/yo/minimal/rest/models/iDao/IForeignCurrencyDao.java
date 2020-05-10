package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.ForeignCurrency;
import org.springframework.data.repository.CrudRepository;

public interface IForeignCurrencyDao extends CrudRepository<ForeignCurrency, Long> {
}