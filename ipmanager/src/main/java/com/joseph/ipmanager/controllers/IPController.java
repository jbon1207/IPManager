package com.joseph.ipmanager.controllers;


import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.enums.IPState;
import com.joseph.ipmanager.pojos.IPRequest;
import com.joseph.ipmanager.services.IPAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class IPController {

    @Autowired
    private IPAddressService ipAddressService;

    @RequestMapping(value = "/ip/generate", method = RequestMethod.POST)
    public ResponseEntity<?> generateIp(@RequestBody IPRequest ipRequest){
        return null;
        //return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/ip/reserve", method = RequestMethod.POST)
    public ResponseEntity<?> reserveIp(@Valid @RequestBody IPRequest ipRequest){

        IPAddress ipAddress = new IPAddress();
        ipAddress.setResourceState(IPState.RESERVED);
        ipAddress.setPoolId(ipRequest.getPoolId());
        ipAddress.setValue(ipRequest.getIpAddress());

        ipAddressService.addIP(ipAddress);

        return new ResponseEntity<>(ipAddress, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/ip/blacklist", method = RequestMethod.POST)
    public ResponseEntity<?> blacklistIp(@RequestBody IPRequest ipRequest){
        return null;
        //return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/ip/free", method = RequestMethod.POST)
    public ResponseEntity<?> freeIp(@RequestBody IPRequest ipRequest){
        return null;
        //return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }
}
