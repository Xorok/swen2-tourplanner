package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_SUBPATH;

public class TourMemoryRepository implements TourRepository {

    private final List<Tour> tours;

    public TourMemoryRepository() {
        tours = new ArrayList<>();
        setupTestData();
    }

    private void setupTestData() {
        tours.add(new Tour(
                UUID.randomUUID(),
                "A short hike",
                "This is an easy hike up Mugel mountain.",
                new Point2D.Double(16.229345, 48.007896),
                new Point2D.Double(16.21841, 48.017254),
                TourType.HIKE,
                5016,
                Duration.ofMinutes(90),
                RES_SUBPATH + "map-placeholder/map-placeholder.png"
        ));
        tours.add(new Tour(
                UUID.randomUUID(),
                "The best bike trail",
                "This is a bike trail.",
                new Point2D.Double(16.229345, 48.007896),
                new Point2D.Double(16.21841, 48.017254),
                TourType.HIKE,
                5016,
                Duration.ofMinutes(90),
                RES_SUBPATH + "map-placeholder/map-placeholder2.png"
        ));
        tours.add(new Tour(
                UUID.randomUUID(),
                "Trail running route",
                "Trail running in the countryside of Austria.",
                new Point2D.Double(16.229345, 48.007896),
                new Point2D.Double(16.21841, 48.017254),
                TourType.HIKE,
                5016,
                Duration.ofMinutes(90),
                RES_SUBPATH + "map-placeholder/map-placeholder3.png"
        ));
    }

    @Override
    public List<Tour> findAll() {
        return tours;
    }

    @Override
    public Tour save(Tour entity) {
        tours.add(entity);

        return entity;
    }

    @Override
    public Optional<Tour> findById(UUID id) {
        return tours.stream()
                .filter(tour -> tour.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Tour> queryNames(String searchTerm) {
        return tours.stream() // TODO: Add locale to toLowerCase methods
                .filter(tour -> tour.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
}
