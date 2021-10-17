package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.Bank;

import java.util.List;

public interface IBankService {

    List<Bank> findAllBanks();
}
