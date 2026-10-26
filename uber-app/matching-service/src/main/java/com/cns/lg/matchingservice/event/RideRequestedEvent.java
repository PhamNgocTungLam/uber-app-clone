package com.cns.lg.matchingservice.event;

/*
 * Event consumed from Kafka topic: ride.requested
 * Published by ride service when a rider requests a ride
 * */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestedEvent {

    private String riderId;

    private String rideId;

    private double pickupLatitude;

    private double pickupLongitude;

    private String pickupAddress;

    private double dropLatitude;

    private double dropLongitude;

    private String dropAddress;
}
