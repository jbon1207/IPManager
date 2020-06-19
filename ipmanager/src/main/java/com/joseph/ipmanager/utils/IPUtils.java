package com.joseph.ipmanager.utils;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.stereotype.Component;

@Component
public class IPUtils {

    /* TODO implement method */
    public boolean isIPWithinRange(String lowerBound, String upperBound, String ipAddress){

        SubnetUtils subnetUtils = new SubnetUtils(lowerBound, upperBound);
        return (subnetUtils).getInfo().isInRange(ipAddress);

    }


}
