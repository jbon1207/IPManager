package com.joseph.ipmanager.pojos;

import com.joseph.ipmanager.validators.IPValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IPRequest {

    @IPValue
    public String ipAddress;

    public long poolId;

    public long ipRange;

}

