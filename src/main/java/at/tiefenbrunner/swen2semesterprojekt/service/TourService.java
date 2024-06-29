package at.tiefenbrunner.swen2semesterprojekt.service;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> findTourByNameContains(String term) {
        return tourRepository.queryTours(term);
    }

    public List<Tour> findAllTours() {
        return tourRepository.findAllTours();
    }

    public Optional<Tour> findToursById(UUID id) {
        return tourRepository.findTourById(id);
    }

    public void deleteTour(UUID id) {
        tourRepository.deleteTour(id);
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.saveTour(tour);
    }

    public List<TourLog> findAllLogsByTourId(UUID id) {
        return tourRepository.findLogsByTourId(id);
    }
}
