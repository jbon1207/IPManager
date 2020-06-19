package com.joseph.ipmanager.services;

import com.joseph.ipmanager.entities.IPAddress;
import com.joseph.ipmanager.entities.IPPool;
import com.joseph.ipmanager.enums.IPState;
import com.joseph.ipmanager.exceptions.*;
import com.joseph.ipmanager.repositories.IPAddressRepository;
import com.joseph.ipmanager.repositories.IPPoolRepository;
import com.joseph.ipmanager.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class IPAddressService {

    @Autowired
    private IPAddressRepository ipAddressRepository;

    @Autowired
    private IPPoolRepository ipPoolRepository;

    @Autowired
    private IPPoolService ipPoolService;

    @Autowired
    private IPUtils ipUtils;

    private IPAddress addIP(IPAddress ipAddress) {
        return ipAddressRepository.save(ipAddress);
    }

    public IPAddress findByPoolIdAndValue(Long poolId, String value) throws IPAddressExists {
        List<IPAddress> ipAddresses = ipAddressRepository.findByPoolIdAndValue(poolId, value);
        if (ipAddresses.isEmpty()) {
            throw new IPAddressExists();
        }
        return  ipAddresses.get(0);
    }

    public List<IPAddress> generateIPs(Long poolId, Long range) throws ResourceNotFoundException, PoolLimitReachedException, UnknownHostException, InvalidRangeException {

        Long remaining = range;
        String nextIP= "";

        Optional<IPPool> pool = ipPoolRepository.findById(poolId);
        List<IPAddress> generatedIPAddress = new ArrayList<>();

        if (!pool.isPresent()) {
            throw new ResourceNotFoundException("IP Pool", poolId);
        }

        if(range <=0 ){
            throw new InvalidRangeException();
        }

        IPPool ipPool = pool.get();

        if((ipPool.getTotalCapacity() - ipPool.getUsedCapacity()) < range){
            throw new PoolLimitReachedException();
        }

        /* Get All IPs within a Pool */
        List<IPAddress> poolIPAddresses = ipAddressRepository.findByPoolId(poolId);

        /* Sort the list by IP Address */
        ipUtils.sortIPList(poolIPAddresses);

        /* if no IPs were found registered to the pool OR the most lower bound IP is available, this value is to be set with the low bound ip*/
        if(poolIPAddresses.isEmpty() || !poolIPAddresses.get(0).getValue().equals(ipPool.getLowerBound())){
            IPAddress ipAddress = new IPAddress(ipPool.getId(),ipPool.getLowerBound(), IPState.RESERVED);
            poolIPAddresses.add(0, ipAddress);
            generatedIPAddress.add(ipAddress);
            ipPoolService.incrementPoolUsedCapacity(ipAddress.getPoolId());
            this.addIP(ipAddress);
            remaining--;
        }

        /* Go through the sorted list and always populate next empty value with the next available IP */
        for (int i = 0; i<poolIPAddresses.size(); i++) {
            if (remaining <= 0) {
                break;
            }
            nextIP = ipUtils.getNextIP(poolIPAddresses.get(i).getValue());

            /* If the end of the pool list is reached OR the next available IP does not match
               the next value in the list insert the new IP in the next available slot */
            if (i == poolIPAddresses.size() - 1 || !nextIP.equals(poolIPAddresses.get(i+1).getValue())) {
                IPAddress ipAddress = new IPAddress(ipPool.getId(), nextIP, IPState.RESERVED);
                poolIPAddresses.add(i + 1, ipAddress);
                generatedIPAddress.add(ipAddress);

                this.addIP(ipAddress);
                remaining--;

                ipPoolService.incrementPoolUsedCapacity(ipPool.getId(), range);
            }
        }

       return generatedIPAddress;

    }
    public IPAddress freeIp(IPAddress ipAddress) throws ResourceNotFoundException {
        Optional<IPPool> pool = ipPoolRepository.findById(ipAddress.getPoolId());

        /* Check that the Pool ID exists */
        if (!pool.isPresent()) {
            throw new ResourceNotFoundException("IP Pool", ipAddress.getPoolId());
        }

        /* Check that the IP Address Exists in the Database */
        List<IPAddress> ipAddresses = ipAddressRepository.findByPoolIdAndValue(ipAddress.getPoolId(), ipAddress.getValue());

        if (ipAddresses.isEmpty()){
            throw new ResourceNotFoundException(ipAddress.getId());
        }

        /* Remove IP from the table and decrement the pool availability count */
        ipAddressRepository.delete(ipAddresses.get(0));
        ipPoolService.incrementPoolUsedCapacity(ipAddress.getPoolId(),-1L);

        return null;
    }


    public IPAddress registerIp(IPAddress ipAddress) throws ResourceNotFoundException, IPAddressExists, IPOutOfRangeException {

        Optional<IPPool> pool = ipPoolRepository.findById(ipAddress.getPoolId());

        /* Check that the Pool ID exists */
        if (!pool.isPresent()) {
            throw new ResourceNotFoundException("IP Pool", ipAddress.getPoolId());
        }

        /* Check that the IP Address Available */
        List<IPAddress> ipAddresses = ipAddressRepository.findByPoolIdAndValue(ipAddress.getPoolId(), ipAddress.getValue());

        if (!ipAddresses.isEmpty()){
            throw new IPAddressExists();
        }

        /* Validate that the IP Address is within the Pool Range */
        if(!ipUtils.isIPWithinRange(pool.get().getLowerBound(), pool.get().getUpperBound(), ipAddress.getValue())){
            throw new IPOutOfRangeException();
        }

        ipPoolService.incrementPoolUsedCapacity(ipAddress.getPoolId());

        return this.addIP(ipAddress);
    }

}
