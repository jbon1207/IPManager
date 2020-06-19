package com.joseph.ipmanager.repositories;

import com.joseph.ipmanager.entities.IPAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface IPAddressRepository extends CrudRepository<IPAddress, Long> {

    Set<IPAddress> findByValue(String ipAddress);

}
