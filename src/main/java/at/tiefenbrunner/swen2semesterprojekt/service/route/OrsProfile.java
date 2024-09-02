package at.tiefenbrunner.swen2semesterprojekt.service.route;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourType;

public enum OrsProfile {
    DRIVING_CAR("driving-car"),
    DRIVING_HGV("driving-hgv"),
    CYCLING_REGULAR("cycling-regular"),
    CYCLING_ROAD("cycling-road"),
    CYCLING_MOUNTAIN("cycling-mountain"),
    CYCLING_ELECTRIC("cycling-electric"),
    FOOT_WALKING("foot-walking"),
    FOOT_HIKING("foot-hiking"),
    WHEELCHAIR("wheelchair");

    private final String profileString;

    private OrsProfile(String profileString) {
        this.profileString = profileString;
    }

    public static OrsProfile mapFrom(TourType tourType) {
        return switch (tourType) {
            case BIKE -> CYCLING_REGULAR;
            case HIKE -> FOOT_HIKING;
            case RUNNING, VACATION -> FOOT_WALKING;
        };
    }

    public String toString() {
        return profileString;
    }
}
