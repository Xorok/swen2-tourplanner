package at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    private double x;
    private double y;

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}