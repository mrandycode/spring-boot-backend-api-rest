package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryDao extends CrudRepository<Category, Long> {
//    @Transactional
//    @Modifying
//    @Query("update CClass aClass set aClass.status = 'C' where aClass.id = term")
//    void deleteClassById(Long term, String status);
}