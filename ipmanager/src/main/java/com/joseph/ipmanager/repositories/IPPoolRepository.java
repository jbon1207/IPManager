package com.joseph.ipmanager.repositories;

import com.joseph.ipmanager.entities.IPPool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface IPPoolRepository extends CrudRepository<IPPool, Long> {

}
