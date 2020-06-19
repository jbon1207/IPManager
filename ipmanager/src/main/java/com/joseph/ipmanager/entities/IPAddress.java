package com.joseph.ipmanager.entities;

import com.joseph.ipmanager.enums.IPState;
import com.joseph.ipmanager.validators.IPValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class IPAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column (name="pool_id")
    private long poolId;

    @Column (name="value")
    @IPValue
    private String value;

    /* TODO: change to enum & validate */
    private IPState resourceState;


}
