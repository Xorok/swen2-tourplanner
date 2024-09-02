package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_tour")
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
    @OneToMany(mappedBy = "tour", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    private List<TourLog> tourLogs; // TODO: Not fully implemented in TourMemoryRepository

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourPoint> tourPoints; // TODO: Not fully implemented in TourMemoryRepository
}
