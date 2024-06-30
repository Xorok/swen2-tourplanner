package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tl_tour_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tl_id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tl_t_tour", nullable = false)
    private Tour tour;

    @Column(name = "tl_timestamp", nullable = false)
    private Instant dateTime;

    @Column(name = "tl_comment", nullable = false)
    private String comment;

    @Column(name = "tl_distance_m", nullable = false)
    private Integer totalDistanceM;

    @Column(name = "tl_time_min", nullable = false)
    private Duration totalTime;

    @Column(name = "tl_difficulty", nullable = false)
    @Enumerated(EnumType.STRING)
    private TourDifficulty difficulty;

    @Column(name = "tl_rating", nullable = false)
    private Integer rating;
}
