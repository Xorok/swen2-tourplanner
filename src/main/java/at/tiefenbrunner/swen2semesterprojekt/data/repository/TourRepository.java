package at.tiefenbrunner.swen2semesterprojekt.data.repository;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourPoint;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    Tour saveTour(Tour entity);

    void insertAllTours(List<Tour> entity);

    void deleteTour(UUID id);

    int deleteAllTours();

    List<Tour> findAllTours();

    Optional<Tour> findTourById(UUID id);

    List<Tour> fullTextTourSearch(String searchTerm);

    void saveTourPoints(List<TourPoint> points);

    int deleteAllTourPoints(UUID tourId);

    List<TourPoint> findRouteByTourId(UUID tourId);

    TourLog saveTourLog(TourLog entity);

    void deleteTourLog(UUID id);

    Optional<TourLog> findTourLogById(UUID id);

    List<TourLog> findTourLogsByTourId(UUID id);
}
