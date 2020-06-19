package com.joseph.ipmanager.services;

import com.joseph.ipmanager.entities.IPPool;
import com.joseph.ipmanager.exceptions.ResourceNotFoundException;
import com.joseph.ipmanager.repositories.IPPoolRepository;
import com.joseph.ipmanager.validators.IPValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;

@Service
@Transactional
public class IPPoolService {

    @Autowired
    private IPPoolRepository ipPoolRepository;

    public IPPool editPool(Long poolId, IPPool pool) throws ResourceNotFoundException {
        return ipPoolRepository.findById(poolId).map(p -> {
            p.setDescription(pool.getDescription());
            p.setTotalCapacity(pool.getTotalCapacity());
            p.setUsedCapacity(pool.getUsedCapacity());
            p.setLowerBound(pool.getLowerBound());
            p.setUpperBound(pool.getUpperBound());
            return ipPoolRepository.save(p);
        }).orElseThrow(() -> new ResourceNotFoundException(poolId));
    }

    public void incrementPoolUsedCapacity(Long poolId) throws ResourceNotFoundException {
        incrementPoolUsedCapacity(poolId, 1L);
    }

    public void incrementPoolUsedCapacity(Long poolId, Long increment) throws ResourceNotFoundException {
        IPPool pool = ipPoolRepository.findById(poolId).get();
        pool.setUsedCapacity(pool.getUsedCapacity()+increment);
        editPool(poolId, pool);
    }

}