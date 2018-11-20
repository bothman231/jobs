package com.botham.thing.db;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.botham.thing.persist.Thing;


@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
public interface ThingRepository extends PagingAndSortingRepository<Thing, Integer> {

    public List<Thing> findAll();
    
    public List<Thing> findByThingDescriptionLike(String search);
    
}