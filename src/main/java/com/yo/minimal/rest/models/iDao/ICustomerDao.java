package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerDao extends CrudRepository<Customer, Long> {

    @Query("select cust from Customer cust where cust.nacionality = ?1 and cust.identificationId = ?2")
    public Customer searhCustomerByIdentificationId(String nacionality, String identificactionId);
}
