package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Tour {
    private UUID id;
    private String name;
    private String description;
    private Point2D.Double from;
    private Point2D.Double to;
    private TourType tourType;
    private double distanceM;
    private Duration estimatedTime;
    private String routeMapImg;
}
