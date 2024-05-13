package at.tiefenbrunner.swen2semesterprojekt.repository;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourRepository {

    List<Tour> findAll();

    Tour save(Tour entity);

    Optional<Tour> findById(UUID id);

    List<Tour> queryNames(String searchTerm);
}
