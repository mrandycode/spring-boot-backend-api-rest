package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.AttributesItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAttributesItemDao extends PagingAndSortingRepository<AttributesItem, Long> {
}