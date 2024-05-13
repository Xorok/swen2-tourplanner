package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Tour {
    private UUID id;
    private String name;
    private String description;
    private String from; // Point2D.Double?
    private String to;
    private TourType tourType;
    private double distanceM;
    private Duration estimatedTime;
    private String routeMapImg;
}
