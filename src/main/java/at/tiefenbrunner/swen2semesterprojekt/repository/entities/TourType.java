package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tt_tour_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourType {
    @Id
    @Column(name = "tt_id")
    private String id;
}
