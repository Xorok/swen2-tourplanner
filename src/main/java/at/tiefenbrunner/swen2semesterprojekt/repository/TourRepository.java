package at.tiefenbrunner.swen2semesterprojekt.repository;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    List<Tour> findAllTours();

    Tour saveTour(Tour entity);

    void deleteTour(UUID id);

    Optional<Tour> findTourById(UUID id);

    List<Tour> queryTourNames(String searchTerm);
    
    List<TourLog> findLogsByTourId(UUID id);
}
