package com.cns.lg.rideservice.controller;

import com.cns.lg.rideservice.dto.req.RideRequest;
import com.cns.lg.rideservice.dto.res.RideResponse;
import com.cns.lg.rideservice.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
@Slf4j
public class RideController {

    private final RideService rideService;

    //Rider request a new ride
    @PostMapping("/request")
    public ResponseEntity<RideResponse> requestRide(
            @Valid @RequestBody RideRequest req){
        log.info("Ride request received from rider: {}", req.getRiderId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rideService.requestRide(req));
    }

    @GetMapping("/{riderId}")
    public ResponseEntity<RideResponse> responseRide(
            @PathVariable String riderId
    ){
        return ResponseEntity.ok(rideService.getRideById(riderId));
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<List<RideResponse>> getRidesByRider(
            @PathVariable String riderId
    ){
        return ResponseEntity.ok(rideService.getRidesByRider(riderId));
    }

    // Driver starts the Ride
    @PutMapping("/{rideId}/start")
    public ResponseEntity<RideResponse> startRide(
            @PathVariable String rideId
    ){
        return ResponseEntity.ok(rideService.startRide(rideId));
    }

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String rideId
    ){
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponse> cancelRide(
            @PathVariable String rideId
    ){
        return ResponseEntity.ok(rideService.cancelRide(rideId));
    }
}
