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

    public Tour saveTour(Tour tour) {
        return tourRepository.saveTour(tour);
    }

    public void deleteTour(UUID id) {
        tourRepository.deleteTour(id);
    }

    public List<Tour> findAllTours() {
        return tourRepository.findAllTours();
    }

    public Optional<Tour> findTourById(UUID id) {
        return tourRepository.findTourById(id);
    }

    public List<Tour> findTours(String term) {
        return tourRepository.fullTextTourSearch(term);
    }

    public TourLog saveLog(TourLog log) {
        return tourRepository.saveTourLog(log);
    }

    public void deleteLog(UUID id) {
        tourRepository.deleteTourLog(id);
    }

    public Optional<TourLog> findLogById(UUID id) {
        return tourRepository.findTourLogById(id);
    }

    public List<TourLog> findAllLogsByTourId(UUID id) {
        return tourRepository.findTourLogsByTourId(id);
    }
}
