package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.SubCategory;
import org.springframework.data.repository.CrudRepository;

public interface ISubcategoryDao extends CrudRepository<SubCategory, Long> {
}