package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Entity
@Table(name = "t_tour")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "t_id")
    private UUID id;

    @Column(name = "t_name", nullable = false)
    private String name;

    @Column(name = "t_desc", nullable = false)
    private String description;

    @Column(name = "t_from", nullable = false)
    private String from; // Point2D.Double?

    @Column(name = "t_to", nullable = false)
    private String to;

    @ManyToOne
    @JoinColumn(name = "t_tt_type", nullable = false)
    private TourType tourType;

    @Column(name = "t_distance_m", nullable = true)
    private Integer distanceM;

    @Column(name = "t_time_min", nullable = true)
    private Duration estimatedTime;

    @Column(name = "t_route_img", nullable = true)
    private String routeMapImg;
}
