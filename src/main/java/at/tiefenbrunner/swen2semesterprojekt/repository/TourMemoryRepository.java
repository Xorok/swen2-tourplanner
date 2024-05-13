package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;

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
                UUID.fromString("e24b14cd-945c-4bab-b144-0bedf3d65237"),
                "Mugel mountain (Styria)",
                "This is an easy hike up Mugel mountain.",
                "Winter parking lot Niklasdorf, Styria, Austria", // new Point2D.Double(16.229345, 48.007896)?
                "Mugel mountain, Styria, Austria",
                TourType.HIKE,
                5016,
                Duration.ofMinutes(90),
                RES_SUBPATH + "map-placeholder/map-placeholder1.jpg"
        ));
        tours.add(new Tour(
                UUID.fromString("ff9ef350-3ff6-4391-9758-b81fa7f57095"),
                "Bike Trail - Saalach Valley",
                "This is a long bike trail.",
                "Bruck, Salzburg",
                "Salzburg Hauptbahnhof (S-Bahn)",
                TourType.BIKE,
                1200,
                Duration.ofMinutes(530),
                RES_SUBPATH + "map-placeholder/map-placeholder2.jpg"
        ));
        tours.add(new Tour(
                UUID.fromString("3c721dec-9b72-4180-b26d-295b6a26a11d"),
                "Lahngang lake Trail running route",
                "This is a trail with scenic views.",
                "Parking lot Gößl, Salzburg",
                "Drausengatterl",
                TourType.RUNNING,
                18530,
                Duration.ofMinutes(300),
                RES_SUBPATH + "map-placeholder/map-placeholder3.jpg"
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
