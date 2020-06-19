package com.joseph.ipmanager.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IPState {
    @JsonProperty("BLACKLISTED") BLACKLISTED,
    @JsonProperty("RESERVED") RESERVED,
    @JsonProperty("FREE") FREE
}
