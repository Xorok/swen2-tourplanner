package at.tiefenbrunner.swen2semesterprojekt.service;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> findByNameContains(String term) {
        return tourRepository.queryNames(term);
    }

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    public Optional<Tour> findById(UUID id) {
        return tourRepository.findById(id);
    }
}
