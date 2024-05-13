package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
    private @Nullable UUID id; // TODO: Make separate model class for creation to get rid of Nullable
    private String name;
    private String description;
    private String from; // Point2D.Double?
    private String to;
    private TourType tourType;
    private double distanceM;
    private Duration estimatedTime;
    private String routeMapImg;
}
