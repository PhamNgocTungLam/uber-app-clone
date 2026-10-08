package com.cns.lg.rideservice.dto.res;

import com.cns.lg.rideservice.enums.RideStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class RideResponse {
    private String id;

    private String riderId;

    private String driverId;

    private double pickupLatitude;

    private double pickupLongitude;

    private String pickupAddress;

    private double dropLatitude;

    private double dropLongitude;

    private String dropAddress;

    //Ride status - tracks the lifecycle
    private RideStatus status;

    //Fare details
    private BigDecimal estimatedFare;

    private BigDecimal actualFare;

    //Timestamps
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}
