package at.tiefenbrunner.swen2semesterprojekt.util;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_SUBPATH;

public class TestDataGenerator {
    public static void setupTestData(TourRepository tourRepository) {
        if (!tourRepository.findAllTours().isEmpty()) {
            return;
        }

        Tour tour1 = new Tour(
                null,
                "Mugel mountain (Styria)",
                "This is an easy hike up Mugel mountain.",
                "Winter parking lot Niklasdorf, Styria, Austria", // new Point2D.Double(16.229345, 48.007896)?
                "Mugel mountain, Styria, Austria",
                TourType.HIKE,
                5016,
                Duration.ofMinutes(90),
                RES_SUBPATH + "map-placeholder/map-placeholder1.jpg"
        );
        Tour tour2 = new Tour(
                null,
                "Bike Trail - Saalach Valley",
                "This is a long bike trail.",
                "Bruck, Salzburg",
                "Salzburg Hauptbahnhof (S-Bahn)",
                TourType.BIKE,
                1200,
                Duration.ofMinutes(530),
                RES_SUBPATH + "map-placeholder/map-placeholder2.jpg"
        );
        Tour tour3 = new Tour(
                null,
                "Lahngang lake Trail running route",
                "This is a trail with scenic views.",
                "Parking lot Gößl, Salzburg",
                "Drausengatterl",
                TourType.RUNNING,
                18530,
                Duration.ofMinutes(300),
                RES_SUBPATH + "map-placeholder/map-placeholder3.jpg"
        );
        tourRepository.saveTour(tour1);
        tourRepository.saveTour(tour2);
        tourRepository.saveTour(tour3);

        TourLog tourLog1 = new TourLog(
                UUID.randomUUID(),
                tour1,
                Instant.now().minus(3, ChronoUnit.DAYS),
                "Luckily there was good weather.",
                tour1.getDistanceM() - 20,
                tour1.getEstimatedTime().minus(10, ChronoUnit.MINUTES),
                TourDifficulty.MEDIUM,
                75
        );
        TourLog tourLog2 = new TourLog(
                UUID.randomUUID(),
                tour1,
                Instant.now().minus(6, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS),
                "I brought my dog with me.",
                tour1.getDistanceM() - 60,
                tour1.getEstimatedTime().plus(5, ChronoUnit.MINUTES),
                TourDifficulty.EASY,
                85
        );
        TourLog tourLog3 = new TourLog(
                UUID.randomUUID(),
                tour2,
                Instant.now().minus(20, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS),
                "Great with friends.",
                tour2.getDistanceM(),
                tour2.getEstimatedTime().minus(30, ChronoUnit.MINUTES),
                TourDifficulty.MEDIUM,
                80
        );
        TourLog tourLog4 = new TourLog(
                UUID.randomUUID(),
                tour3,
                Instant.now().minus(70, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS),
                "Not recommended if it's too hot.",
                tour3.getDistanceM() - 10000,
                tour3.getEstimatedTime().minus(210, ChronoUnit.MINUTES),
                TourDifficulty.HARD,
                40
        );

        tourRepository.saveTourLog(tourLog1);
        tourRepository.saveTourLog(tourLog2);
        tourRepository.saveTourLog(tourLog3);
        tourRepository.saveTourLog(tourLog4);
    }
}
