package at.tiefenbrunner.swen2semesterprojekt.util;

import java.time.Duration;

public class DurationDecimalTimeConverter {
    public static Duration fromDecimalSeconds(double seconds) {
        double nanos = seconds * Duration.ofSeconds(1).toNanos();
        return Duration.ofNanos(Math.round(nanos));
    }
}
