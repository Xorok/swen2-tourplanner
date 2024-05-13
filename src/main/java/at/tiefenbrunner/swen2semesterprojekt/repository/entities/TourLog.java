package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourLog {
    private UUID tourId;
    private Instant dateTime;
    private String comment;
    private TourDifficulty difficulty;
    private double totalDistanceM;
    private Duration totalTime;
    private Integer rating;
}
