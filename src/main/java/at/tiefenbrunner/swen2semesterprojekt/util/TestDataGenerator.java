package at.tiefenbrunner.swen2semesterprojekt.util;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.*;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_ASSETS_SUBPATH;

public class TestDataGenerator {
    public static void setupTestData(TourRepository tourRepository) {
        if (!tourRepository.findAllTours().isEmpty()) {
            return;
        }

        Tour tour1 = new Tour(
                null,
                "Mugel mountain (Styria)",
                "This is an easy hike up Mugel mountain.",
                new Point(15.179389, 47.381082),
                new Point(15.187986, 47.358484),
                TourType.HIKE,
                5016,
                Duration.ofMinutes(90),
                RES_ASSETS_SUBPATH + "map-placeholder.jpg",
                null
        );
        Tour tour2 = new Tour(
                null,
                "Bike Trail - Saalach Valley",
                "This is a long bike trail.",
                new Point(15.179389, 47.381082),
                new Point(15.187986, 47.358484),
                TourType.BIKE,
                1200,
                Duration.ofMinutes(530),
                RES_ASSETS_SUBPATH + "map-placeholder.jpg",
                null
        );
        Tour tour3 = new Tour(
                null,
                "Lahngang lake Trail running route",
                "This is a trail with scenic views.",
                new Point(15.179389, 47.381082),
                new Point(15.187986, 47.358484),
                TourType.RUNNING,
                18530,
                Duration.ofMinutes(300),
                RES_ASSETS_SUBPATH + "map-placeholder.jpg",
                null
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
