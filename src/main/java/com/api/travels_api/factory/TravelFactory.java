package com.api.travels_api.factory;

import com.api.travels_api.model.Travel;

public interface TravelFactory {
    Travel createTravel (String type);
}
