package com.api.travels_api.factory.impl;

import com.api.travels_api.enumeration.TravelTypeEnum;
import com.api.travels_api.factory.TravelFactory;
import com.api.travels_api.model.Travel;

public class TravelFactoryImpl implements TravelFactory {
    @Override
    public Travel createTravel (String type) {
        if(TravelTypeEnum.ONE_WAY.getValue().equals(type)) {
            return new Travel(TravelTypeEnum.ONE_WAY);
        } else if (TravelTypeEnum.MULTI_CITY.getValue().equals(type)) {
            return new Travel();
        }
        return new Travel();
    }
}
