package at.tiefenbrunner.swen2semesterprojekt.repository;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    Tour saveTour(Tour entity);

    void deleteTour(UUID id);

    List<Tour> findAllTours();

    Optional<Tour> findTourById(UUID id);

    List<Tour> queryTours(String searchTerm);

    TourLog saveTourLog(TourLog entity);

    void deleteTourLog(UUID id);

    Optional<TourLog> findTourLogById(UUID id);

    List<TourLog> findTourLogsByTourId(UUID id);
}
