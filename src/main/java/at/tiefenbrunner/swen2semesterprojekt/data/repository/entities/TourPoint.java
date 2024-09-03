package at.tiefenbrunner.swen2semesterprojekt.data.repository.entities;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "tp_tour_point")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tp_id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tp_t_id", nullable = false)
    private Tour tour;

    @Column(name = "tp_num", nullable = false)
    private BigInteger num;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "tp_lat", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "tp_long", nullable = false))
    })
    private Point point;
}
