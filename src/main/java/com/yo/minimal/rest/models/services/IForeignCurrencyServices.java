package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.ForeignCurrency;

import java.util.List;


public interface IForeignCurrencyServices {

    public List<ForeignCurrency> findAllForeignCurrency();

    public ForeignCurrency findByIdForeignCurrency(Long id);

    public ForeignCurrency saveForeignCurrency(ForeignCurrency foreignCurrency);

}
