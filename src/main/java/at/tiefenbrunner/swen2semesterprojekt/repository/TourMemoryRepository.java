package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_SUBPATH;

public class TourMemoryRepository implements TourRepository {

    private final List<Tour> tours;
    private final List<TourLog> tourLogs;

    public TourMemoryRepository() {
        tours = new ArrayList<>();
        tourLogs = new ArrayList<>();
        setupTestData();
    }

    private void setupTestData() {
        UUID tourId1 = UUID.fromString("e24b14cd-945c-4bab-b144-0bedf3d65237");
        UUID tourId2 = UUID.fromString("ff9ef350-3ff6-4391-9758-b81fa7f57095");
        UUID tourId3 = UUID.fromString("3c721dec-9b72-4180-b26d-295b6a26a11d");

        Tour tour1 = new Tour(
                tourId1,
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
                tourId2,
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
                tourId3,
                "Lahngang lake Trail running route",
                "This is a trail with scenic views.",
                "Parking lot Gößl, Salzburg",
                "Drausengatterl",
                TourType.RUNNING,
                18530,
                Duration.ofMinutes(300),
                RES_SUBPATH + "map-placeholder/map-placeholder3.jpg"
        );
        tours.add(tour1);
        tours.add(tour2);
        tours.add(tour3);

        tourLogs.add(new TourLog(
                tourId1,
                Instant.now().minus(3, ChronoUnit.DAYS),
                "Luckily there was good weather.",
                TourDifficulty.MEDIUM,
                tour1.getDistanceM() - 20,
                tour1.getEstimatedTime().minus(10, ChronoUnit.MINUTES),
                75
        ));
        tourLogs.add(new TourLog(
                tourId1,
                Instant.now().minus(6, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS),
                "I brought my dog with me.",
                TourDifficulty.EASY,
                tour1.getDistanceM() - 60,
                tour1.getEstimatedTime().plus(5, ChronoUnit.MINUTES),
                85
        ));
        tourLogs.add(new TourLog(
                tourId2,
                Instant.now().minus(20, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS),
                "Great with friends.",
                TourDifficulty.MEDIUM,
                tour2.getDistanceM(),
                tour2.getEstimatedTime().minus(30, ChronoUnit.MINUTES),
                80
        ));
        tourLogs.add(new TourLog(
                tourId3,
                Instant.now().minus(70, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS),
                "Not recommended if it's too hot.",
                TourDifficulty.HARD,
                tour3.getDistanceM() - 10000,
                tour3.getEstimatedTime().minus(210, ChronoUnit.MINUTES),
                40
        ));
    }

    @Override
    public List<Tour> findAllTours() {
        return tours;
    }

    @Override
    public Tour saveTour(Tour newTour) {
        if (newTour.getId() == null) {
            newTour.setId(UUID.randomUUID());
        } else {
            Optional<Tour> entryOpt = findTourById(newTour.getId());
            if (entryOpt.isPresent()) {
                Tour tour = entryOpt.get();
                tour.setName(newTour.getName());
                tour.setDescription(newTour.getDescription());
                tour.setFrom(newTour.getFrom());
                tour.setTo(newTour.getTo());
                tour.setTourType(newTour.getTourType());
                tour.setDistanceM(newTour.getDistanceM());
                return tour;
            }
        }

        tours.add(newTour);
        return newTour;
    }

    @Override
    public void deleteTour(UUID id) {
        tours.removeIf(tour -> tour.getId().equals(id));
    }

    @Override
    public Optional<Tour> findTourById(UUID id) {
        return tours.stream()
                .filter(tour -> tour.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Tour> queryTourNames(String searchTerm) {
        return tours.stream() // TODO: Add locale to toLowerCase methods
                .filter(tour -> tour.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TourLog> findLogsByTourId(UUID id) {
        return tourLogs.stream()
                .filter(log -> log.getTourId().equals(id))
                .collect(Collectors.toList());
    }
}
