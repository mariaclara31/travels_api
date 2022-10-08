package com.api.travels_api.service;

import com.api.travels_api.enumeration.TravelTypeEnum;
import com.api.travels_api.factory.TravelFactory;
import com.api.travels_api.factory.impl.TravelFactoryImpl;
import com.api.travels_api.model.Travel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelService {
    private TravelFactory factory;

    private List<Travel> travels;

    public void createFactory() {
        if(factory == null) {
            factory = new TravelFactoryImpl();
        }
    }

    public void createTravelList() {
        if(travels == null) {
            travels = new ArrayList<>();
        }
    }

    public boolean isJsonValid(String jsonInString) {
        try{
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }
    private long parsId(JSONObject travel) {
        return Long.valueOf((int) travel.get("id"));
    }

    private BigDecimal parseAmount(JSONObject travel) {
        return new BigDecimal((String) travel.get("amount"));
    }

    private LocalDateTime parseStartDate(JSONObject travel) {
        var startDate = (String) travel.get("startDate");
        return ZonedDateTime.parse(startDate).toLocalDateTime();
    }

    private LocalDateTime parseEndDate(JSONObject travel) {
        var endDate = (String) travel.get("endDate");
        return ZonedDateTime.parse(endDate).toLocalDateTime();
    }

    public boolean isStartDateGreaterThanEndDate(Travel travel) {
        if(travel.getEndDate() == null) return false;
        return travel.getStartDate().isAfter(travel.getEndDate());
    }

    private void setTravelValues(JSONObject jsonTravel, Travel travel) {
        String orderNumber = (String) jsonTravel.get("orderNumber");
        String type = (String) jsonTravel.get("type");

        travel.setOrderNumber(orderNumber != null ? orderNumber : travel.getOrderNumber());
        travel.setAmount(jsonTravel.get("amount") != null ? parseAmount(jsonTravel) : travel.getAmount());
        travel.setStartDate(jsonTravel.get("initialDate") != null ? parseStartDate(jsonTravel) : travel.getStartDate());
        travel.setEndDate(jsonTravel.get("finalDate") != null ? parseEndDate(jsonTravel) : travel.getEndDate());
        travel.setType(type != null ? TravelTypeEnum.getEnum(type) : travel.getType());
    }

    public Travel create(JSONObject jsonTravel) {
        createFactory();
        Travel travel = factory.createTravel((String) jsonTravel.get("type"));
        travel.setId(parsId(jsonTravel));
        setTravelValues(jsonTravel, travel);
        return travel;
    }

    public Travel update(Travel travel, JSONObject jsonTravel) {
        setTravelValues(jsonTravel, travel);
        return travel;
    }

    public void add(Travel travel) {
        createTravelList();
        travels.add(travel);
    }

    public List<Travel> find() {
        createTravelList();
        return travels;
    }

    public Travel findById(long id) {
        return travels.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
    }

    public void delete() {
        travels.clear();
    }

    public void clearObjects() {
        travels = null;
        factory = null;
    }
}
