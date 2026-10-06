package com.cns.lg.rideservice.enu;

/**
 * FLOW
 * REQUESTED -> MATCHING -> ACCEPTED -> DRIVER_ARRIVING -> RIDE_STARED -> COMPLETED
 * -> CANCELLED
 */
public enum RideStatus {
    REQUESTED,
    MATCHING,
    ACCEPTED,
    DRIVER_ARRIVING,
    RIDE_STARED,
    COMPLETED,
    CANCELLED
}
