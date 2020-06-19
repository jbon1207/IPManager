package com.joseph.ipmanager.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IPState {
    @JsonProperty("FREE") FREE,
    @JsonProperty("BLACKLISTED") BLACKLISTED,
    @JsonProperty("RESERVED") RESERVED
}
