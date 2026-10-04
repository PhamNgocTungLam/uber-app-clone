package com.cns.lg.locationservice.controller;

import com.cns.lg.locationservice.dto.req.DriverLocationRequest;
import com.cns.lg.locationservice.dto.res.NearByDriverResponse;
import com.cns.lg.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@Slf4j
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    //driver phone calls api this every 3 seconds to update the driver location in redis
    @PostMapping("/drivers/update")
    public ResponseEntity<String> updateDriverLocation(
            @RequestBody DriverLocationRequest req
    ) {
         locationService.updateDriverLocation(req);
         return ResponseEntity.ok("Driver " + req.getDriverId() + " location updated successfully");
    }

    //Matching service calls this api when a rider requests a ride to find nearby drivers
    public ResponseEntity<List<NearByDriverResponse>> getNearByDrivers(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radius
    ){
        return ResponseEntity.ok(locationService.findNearByDrivers(latitude, longitude, radius));
    }

    //called when driver goes offline
    @DeleteMapping("/drivers/{driverID}")
    public ResponseEntity<String> removeDriver(
            @PathVariable String driverID
    ){
        locationService.removeDriver(driverID);
        return ResponseEntity.ok("Driver " + driverID + " removed successfully!");
    }
}
