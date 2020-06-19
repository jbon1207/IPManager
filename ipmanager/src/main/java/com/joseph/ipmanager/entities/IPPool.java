package com.joseph.ipmanager.entities;

import com.joseph.ipmanager.validators.IPValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class IPPool {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    @Id
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "total_capacity")
    private long totalCapacity;

    @Column(name = "user_capacity")
    private long usedCapacity;

    @Column(name = "lower_bound")
    @IPValue
    private String lowerBound;

    @Column(name = "upper_bound")
    @IPValue
    private String upperBound;

}
