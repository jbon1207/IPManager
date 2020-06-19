package com.joseph.ipmanager.repositories;

import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.entities.IPPool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPPoolRepository extends CrudRepository<IPPool, Long> {
}
