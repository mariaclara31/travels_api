package com.api.travels_api.controller;

import com.api.travels_api.model.Travel;
import com.api.travels_api.service.TravelService;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.apache.logging.log4j.status.StatusLogger.getLogger;

@RestController
@RequestMapping("travels_api/travels")
public class TravelController {
    private static final Logger logger = getLogger();

    @Autowired
    private TravelService travelService;

    @PostMapping
    public ResponseEntity<Travel> create(@RequestBody JSONObject trip) {

        try{
            if(travelService.isJsonValid(trip.toString())) {
                Travel tripCreated = travelService.create(trip);
                var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(tripCreated.getOrderNumber()).build().toUri();
                if(travelService.isStartDateGreaterThanEndDate(tripCreated)){
                    logger.error("The start date is greater than end date");
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
                } else {
                    travelService.add(tripCreated);
                    return ResponseEntity.created(uri).body(null);
                }
            }else {
                return ResponseEntity.badRequest().body(null);
            }
        }catch (Exception e) {
            logger.error("JSON fields aren't parsable. " + e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Travel>> find() {
        if(travelService.find().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info(travelService.find());
        return ResponseEntity.ok(travelService.find());
    }
    @DeleteMapping
    public ResponseEntity<Boolean> delete() {
        try{
            travelService.delete();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(path = "/{id}", produces = { "application/json" })
    public ResponseEntity<Travel> update(@PathVariable("id") long id, @RequestBody JSONObject travel) {
        try {
            if (travelService.isJsonValid(travel.toString())) {
                Travel tripToUpdate = travelService.findById(id);
                if(tripToUpdate == null){
                    logger.error("Travel not found.");
                    return ResponseEntity.notFound().build();
                }else{
                    Travel tripUpdated = travelService.update(tripToUpdate, travel);
                    return ResponseEntity.ok(tripUpdated);
                }
            }else  {
                return ResponseEntity.badRequest().body(null);
            }
        }catch (Exception e) {
            logger.error("JSON fields aren't parsable. " + e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
