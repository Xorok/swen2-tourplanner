package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "td_tour_difficulty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDifficulty {

    @Id
    @Column(name = "td_id")
    private String id;
}
