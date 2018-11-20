package com.botham.thing.db;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.botham.thing.persist.Thing;
import com.botham.thing.persist.ThingView;


@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
public interface ThingViewRepository extends PagingAndSortingRepository<ThingView, Integer> {

    public List<ThingView> findAll();
    
    public List<ThingView> findByThingDescriptionLike(String search);
    
    public List<ThingView> findByThingFieldDescriptionLike(String search);
}