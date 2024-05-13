package at.tiefenbrunner.swen2semesterprojekt.service;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;

import java.util.List;

public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> search(String term) {
        return tourRepository.queryNames(term);
    }

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }
}
