package com.api.travels_api.controller;

import com.api.travels_api.model.Statistic;
import com.api.travels_api.model.Travel;
import com.api.travels_api.service.StatisticService;
import com.api.travels_api.service.TravelService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

@RestController
@RequestMapping("travels_api/statistics")
public class StatisticController {
    private static final Logger logger = getLogger();
    @Autowired
    private TravelService travelService;

    @Autowired
    private StatisticService statisticService;

    @GetMapping(produces = { "application/json" })
    public ResponseEntity<Statistic> getStatistics() {
        List<Travel> travels = travelService.find();
        Statistic statistics = statisticService.create(travels);
        logger.info(statistics);
        return ResponseEntity.ok(statistics);
    }
}
