package com.api.travels_api.enumeration;

public enum TravelTypeEnum {
    RETURN("RETURN"), ONE_WAY("ONE_WAY"), MULTI_CITY("MULTI_CITY");
    private String value;

    private TravelTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public static TravelTypeEnum getEnum(String value) {
        for(TravelTypeEnum t : values()) {
            if(value.equals(t.getValue())) {
                return t;
            }
        }
        throw new RuntimeException("Type not found.");
    }
}
