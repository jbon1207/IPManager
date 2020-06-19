package com.joseph.ipmanager.services;

import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.repositories.IPAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPAddressService {

    @Autowired
    private IPAddressRepository ipAddressRepository;


    public IPAddress addIP(IPAddress ipAddress){
        return ipAddressRepository.save(ipAddress);
    }

}
