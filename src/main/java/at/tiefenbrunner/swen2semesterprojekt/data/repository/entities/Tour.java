package at.tiefenbrunner.swen2semesterprojekt.data.repository.entities;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.computed.TourChildFriendliness;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.computed.TourPopularity;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.TourType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_tour")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "t_id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "t_name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "t_desc", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "t_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TourType tourType;

    @Column(name = "t_distance_m", nullable = true)
    private Integer distanceM;

    @Column(name = "t_time", nullable = true)
    private Duration estimatedTime;

    // TODO: Implement lazy fetching, get rid of FetchType.EAGER
    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<TourLog> tourLogs; // TODO: Not fully implemented in TourMemoryRepository

    // TODO: Implement lazy fetching, get rid of FetchType.EAGER
    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<TourPoint> tourPoints; // TODO: Not fully implemented in TourMemoryRepository

    @JsonIgnore
    public TourPopularity getPopularity() {
        if (tourLogs.size() > 20)
            return TourPopularity.HIGH;
        else if (tourLogs.size() > 10)
            return TourPopularity.MEDIUM;
        else return TourPopularity.LOW;
    }

    @JsonIgnore
    public TourChildFriendliness getChildFriendliness() {
        long sumTotalTimes = 0;
        long sumTotalDistances = 0;
        long sumDifficulty = 0;
        double weight = 0.3333d;

        for (TourLog tourLog : tourLogs) {
            sumTotalTimes += tourLog.getTotalTime().toSeconds();
            sumTotalDistances += tourLog.getTotalDistanceM();
            sumDifficulty += tourLog.getDifficulty().toNumberGrade();
        }

        double logSizes = tourLogs.size();
        double avgTotalTimes = sumTotalTimes / logSizes;
        double avgTotalDistances = sumTotalDistances / logSizes;
        int avgDifficulty = (int) Math.round(sumDifficulty / logSizes);

        double offFromEstimatePercent = avgTotalTimes / getEstimatedTime().toSeconds();
        double offFromTourPercent = avgTotalDistances / getDistanceM();
        double difficultyPercent = 1.49 * (1 - (avgDifficulty * weight));

        double total = weight * offFromEstimatePercent + weight * offFromTourPercent + weight * difficultyPercent;
        if (total <= weight)
            return TourChildFriendliness.LOW;
        if (total <= 2 * weight)
            return TourChildFriendliness.MEDIUM;
        return TourChildFriendliness.HIGH;
    }
}
