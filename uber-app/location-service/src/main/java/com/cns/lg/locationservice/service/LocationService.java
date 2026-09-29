package com.cns.lg.locationservice.service;

import com.cns.lg.locationservice.dto.req.DriverLocationRequest;
import com.cns.lg.locationservice.dto.res.NearByDriverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final RedisTemplate<String, String> redisTemplate;

    //Redis key for all driver location
    private static final String DRIVER_GEO_KEY = "driver:locations";

    /*
     * Update driver location in redis
     * Called every 3 second by driver's phone
     * Maps to Redis GEOADD command
     * */
    public void updateDriverLocation(DriverLocationRequest req) {
        log.info("Updating location for driver: {}", req.getDriverId());

        //IMPORTANT: longitude FIRST, latitude SECOND - GeoSpatial Standard
        Point driverPoint = new Point(
                req.getLongitude(),
                req.getLatitude()
        );

        redisTemplate.opsForGeo().add(
                DRIVER_GEO_KEY,
                driverPoint,
                req.getDriverId()
        );
        log.info("Location updated for driver: {}", req.getDriverId());
    }

    /*
     * Find nearby drivers within given radius
     * Called by matching service on ride request
     * Maps to Redis GEORADIUS command
     * */
    public List<NearByDriverResponse> findNearByDrivers(double latitude, double longitude, double radiusInKm) {
        log.info("Finding drivers near lat :{} long: {} withing {}km", latitude, longitude, radiusInKm);

        Circle searchArea = new Circle(
                new Point(longitude, latitude),
                new Distance(radiusInKm, Metrics.KILOMETERS)
        );
        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo().radius(
                        DRIVER_GEO_KEY,
                        searchArea,
                        RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                .includeCoordinates()
                                .includeDistance()
                                .sortAscending()
                                .limit(10)
                );
        List<NearByDriverResponse> res = new ArrayList<>();
        if (results != null) {
            results.getContent().forEach(result -> {
                RedisGeoCommands.GeoLocation<String> location = result.getContent();
                res.add(new NearByDriverResponse(
                        location.getName(),
                        location.getPoint().getY(),
                        location.getPoint().getX(),
                        result.getDistance().getValue()
                ));
            });
        }
        log.info("Found {} driver nearby", res.size());
        return res;
    }

    /*
    * Remove driver when they go offline
    * Maps to redis ZREM command
    * */
    public void removeDriver(String driverID) {
        log.info("Removing driver with id: {}", driverID);
        redisTemplate.opsForGeo().remove(DRIVER_GEO_KEY, driverID);
    }
}
