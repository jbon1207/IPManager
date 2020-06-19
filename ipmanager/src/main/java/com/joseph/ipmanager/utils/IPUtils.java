package com.joseph.ipmanager.utils;

import com.google.common.net.InetAddresses;
import com.joseph.ipmanager.entities.IPAddress;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class IPUtils {

    public boolean isIPWithinRange(String lowerBound, String upperBound, String ipAddress){

        SubnetUtils subnetUtils = new SubnetUtils(lowerBound, upperBound);
        return subnetUtils.getInfo().isInRange(ipAddress);

    }

    public String[] getIPRange(String lowerBound, String upperBound){

        SubnetUtils subnetUtils = new SubnetUtils(lowerBound, upperBound);
        return subnetUtils.getInfo().getAllAddresses();

    }

    public String getNextIP(String ipAddress) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ipAddress);
        return InetAddresses.toAddrString(InetAddresses.increment(address));
    }

    public void sortIPList(List<IPAddress> ipAddresses){
        Collections.sort(ipAddresses, new Comparator<IPAddress>() {
                @Override
                public int compare(IPAddress ip1, IPAddress ip2) {
                    byte[] ip1Value = getInetAddress(ip1.getValue()).getAddress();
                    byte[] ip2Value = getInetAddress(ip2.getValue()).getAddress();

                    for(int i = 0; i < ip1Value.length; i++) {
                        /* Compare each partition of the IP */
                        int i1 = unsignedByteToInt(ip1Value[i]);
                        int i2 = unsignedByteToInt(ip2Value[i]);

                        if(i1 == i2)
                            continue;
                        if(i1 < i2)
                            return -1;
                        else
                            return 1;
                    }
                    return 0;
                }
            });
    }

    private InetAddress getInetAddress(String ipAddress){
        try {
            return InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }


}
