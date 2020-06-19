package com.joseph.ipmanager.controllers;


import com.joseph.ipmanager.config.SwaggerConfig;
import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.enums.IPState;
import com.joseph.ipmanager.exceptions.*;
import com.joseph.ipmanager.pojos.IPRequest;
import com.joseph.ipmanager.services.IPAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.List;

@Controller
@RequestMapping("/api")
@Api(value = "IP Management API", tags = { SwaggerConfig.IP_TAG })
public class IPController {

    @Autowired
    private IPAddressService ipAddressService;

    @ApiOperation(value = "Generates a range of IPs for a specified Pool.")
    @RequestMapping(value = "/ip/generate", method = RequestMethod.POST)
    public ResponseEntity<?> generateIp(@RequestBody IPRequest ipRequest) throws PoolLimitReachedException,
            ResourceNotFoundException, UnknownHostException, InvalidRangeException {
        List<IPAddress> ipAddresses = ipAddressService.generateIPs(ipRequest.poolId, ipRequest.ipRange);
        return new ResponseEntity<>(ipAddresses, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Reserves and IP Address")
    @RequestMapping(value = "/ip/reserve", method = RequestMethod.POST)
    public ResponseEntity<?> reserveIp(@Valid @RequestBody IPRequest ipRequest)
            throws IPAddressExists, ResourceNotFoundException, IPOutOfRangeException {

        IPAddress ipAddress = new IPAddress(ipRequest.getPoolId(), ipRequest.getIpAddress(), IPState.RESERVED);
        ipAddressService.registerIp(ipAddress);

        return new ResponseEntity<>(ipAddress, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Blacklists an IP Address")
    @RequestMapping(value = "/ip/blacklist", method = RequestMethod.POST)
    public ResponseEntity<?> blacklistIp(@Valid @RequestBody IPRequest ipRequest)
            throws IPAddressExists, ResourceNotFoundException, IPOutOfRangeException {

        IPAddress ipAddress = new IPAddress(ipRequest.getPoolId(), ipRequest.getIpAddress(), IPState.BLACKLISTED);
        ipAddressService.registerIp(ipAddress);

        return new ResponseEntity<>(ipAddress, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Frees an IP Address")
    @RequestMapping(value = "/ip/free", method = RequestMethod.POST)
    public ResponseEntity<?> freeIp(@Valid @RequestBody IPRequest ipRequest) throws ResourceNotFoundException {
        IPAddress ipAddress = new IPAddress(ipRequest.getPoolId(), ipRequest.getIpAddress(), IPState.FREE);
        ipAddressService.freeIp(ipAddress);

        return new ResponseEntity<>(ipAddress, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get an IP Address")
    @RequestMapping(value = "/ip/{ipAddressValue}/pools/{poolId}", method = RequestMethod.GET)
    public ResponseEntity<?> getIP(@PathVariable long poolId, @PathVariable String ipAddressValue)
            throws ResourceNotFoundException, IPAddressExists {
        IPAddress ipAddress  = ipAddressService.findByPoolIdAndValue(poolId, ipAddressValue);
        return new ResponseEntity<>(ipAddress, HttpStatus.OK);
    }

}
