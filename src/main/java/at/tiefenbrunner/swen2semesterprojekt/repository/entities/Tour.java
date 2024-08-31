package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

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

    @Column(name = "t_name", nullable = false)
    private String name;

    @Column(name = "t_desc", nullable = false)
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "t_from_lat", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "t_from_long", nullable = false))
    })
    private Point from;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "t_to_lat", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "t_to_long", nullable = false))
    })
    private Point to;

    @Column(name = "t_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TourType tourType;

    @Column(name = "t_distance_m", nullable = true)
    private Integer distanceM;

    @Column(name = "t_time", nullable = true)
    private Duration estimatedTime;

    @Column(name = "t_route_img", nullable = true)
    private String routeMapImg;

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    private List<TourLog> tourLogs; // TODO: Not fully implemented in TourMemoryRepository
}
