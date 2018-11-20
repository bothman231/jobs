package com.botham.thing.db;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.botham.thing.persist.Thing;
import com.botham.thing.persist.ThingField;


@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
public interface ThingFieldRepository extends PagingAndSortingRepository<ThingField, Integer> {

    public List<ThingField> findAll();
    
    public List<ThingField> findByThingFieldDescriptionLike(String search);
    
}