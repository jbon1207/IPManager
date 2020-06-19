package com.joseph.ipmanager.services;

import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.repositories.IPAddressRepository;
import com.joseph.ipmanager.repositories.IPPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPAddressService {

    @Autowired
    private IPAddressRepository ipAddressRepository;

    @Autowired
    private IPPoolRepository ipPoolRepository;

    public IPAddress addIP(IPAddress ipAddress){
        return ipAddressRepository.save(ipAddress);
    }

    public IPAddress reserveIp(IPAddress ipAddress){
        /* Get Pool */

       return this.addIP(ipAddress);
    }

}
