package com.joseph.ipmanager.repositories;

import com.joseph.ipmanager.entities.IPAddress;
import org.springframework.data.repository.CrudRepository;

public interface IPAddressRepository extends CrudRepository<IPAddress, Long> {
}
