package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Location {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private Point point;

    public static Location of(double latitude, double longitude) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        return new Location(point);
    }

    private Location(Point point) {
        this.point = point;
    }

    public double getLatitude() {
        return point.getY();
    }

    public double getLongitude() {
        return point.getX();
    }
}
