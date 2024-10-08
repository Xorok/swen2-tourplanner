package at.tiefenbrunner.swen2semesterprojekt.data.service.route;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.List;

@AllArgsConstructor
@Getter
public class RouteResult {
    List<Point> route;
    Duration duration;
    int distanceInM;
}
