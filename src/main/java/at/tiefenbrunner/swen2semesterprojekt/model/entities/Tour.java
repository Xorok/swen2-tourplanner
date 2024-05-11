package at.tiefenbrunner.swen2semesterprojekt.model.entities;

import java.awt.*;
import java.time.Duration;

public class Tour {
    private Integer id;
    private String name;
    private String description;
    private Point from;
    private Point to;
    private String transportType;
    private double distanceM;
    private Duration estimatedTime;
    private String routeMapImg;
}
