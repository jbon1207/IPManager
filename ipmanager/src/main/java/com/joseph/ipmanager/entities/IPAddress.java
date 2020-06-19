package com.joseph.ipmanager.entities;

import com.joseph.ipmanager.enums.IPState;
import com.joseph.ipmanager.validators.IPValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table
public class IPAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name="pool_id")
    @NotNull
    private long poolId;

    @IPValue
    @NotNull
    private String value;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IPState resourceState;

    public IPAddress(){};

    public IPAddress(long poolId, String value, IPState resourceState){
        this.poolId = poolId;
        this.value = value;
        this.resourceState = resourceState;
    }


}
