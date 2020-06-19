package com.joseph.ipmanager.services;

import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.entities.IPPool;
import com.joseph.ipmanager.exceptions.IPAddressExists;
import com.joseph.ipmanager.exceptions.IPOutOfRangeException;
import com.joseph.ipmanager.exceptions.ResourceNotFoundException;
import com.joseph.ipmanager.repositories.IPAddressRepository;
import com.joseph.ipmanager.repositories.IPPoolRepository;
import com.joseph.ipmanager.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class IPAddressService {

    @Autowired
    private IPAddressRepository ipAddressRepository;

    @Autowired
    private IPPoolRepository ipPoolRepository;

    @Autowired
    private IPUtils ipUtils;

    public IPAddress addIP(IPAddress ipAddress){
        return ipAddressRepository.save(ipAddress);
    }

    public IPAddress reserveIp(IPAddress ipAddress) throws ResourceNotFoundException, IPAddressExists, IPOutOfRangeException {

        Optional<IPPool> pool = ipPoolRepository.findById(ipAddress.getPoolId());

        /* Check that the Pool ID exists */
        if (!pool.isPresent()) {
            throw new ResourceNotFoundException("IP Pool", ipAddress.getPoolId());
        }

        /* Check that the IP Address Available */
        Set<IPAddress> ipAddresses = ipAddressRepository.findByValue(ipAddress.getValue());
        if (!ipAddresses.isEmpty()){
            throw new IPAddressExists();
        }

        /* Validate the IP Address */
        if(!ipUtils.isIPWithinRange(pool.get().getLowerBound(), pool.get().getUpperBound(), ipAddress.getValue())){
            throw new IPOutOfRangeException();
        }

       return this.addIP(ipAddress);
    }

}
