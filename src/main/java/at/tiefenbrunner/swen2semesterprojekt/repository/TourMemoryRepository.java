package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// TODO
public class TourMemoryRepository implements TourRepository {

    private final List<Tour> tours;
    private final List<TourLog> tourLogs;

    public TourMemoryRepository() {
        tours = new ArrayList<>();
        tourLogs = new ArrayList<>();
    }

    @Override
    public Tour saveTour(Tour newTour) {
        if (newTour.getId() == null) {
            newTour.setId(UUID.randomUUID());
        } else {
            Optional<Tour> entryOpt = findTourById(newTour.getId());
            if (entryOpt.isPresent()) {
                Tour tour = entryOpt.get();
                tour.setName(newTour.getName());
                tour.setDescription(newTour.getDescription());
                tour.setFrom(newTour.getFrom());
                tour.setTo(newTour.getTo());
                tour.setTourType(newTour.getTourType());
                tour.setDistanceM(newTour.getDistanceM());
                return tour;
            }
        }

        tours.add(newTour);
        return newTour;
    }

    @Override
    public void deleteTour(UUID id) {
        tours.removeIf(tour -> tour.getId().equals(id));
        tourLogs.removeIf(tourLog -> tourLog.getTour().getId().equals(id));
    }

    @Override
    public List<Tour> findAllTours() {
        return tours;
    }

    @Override
    public Optional<Tour> findTourById(UUID id) {
        return tours.stream()
                .filter(tour -> tour.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Tour> queryTours(String searchTerm) {
        return tours.stream() // TODO: Add locale to toLowerCase methods
                .filter(tour -> tour.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        tour.getDescription().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        tour.getFrom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        tour.getTo().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        tour.getTourType().toString().toLowerCase().contains(searchTerm.toLowerCase()) || // TODO: Future localization
                        tour.getDistanceM().toString().contains(searchTerm.toLowerCase()) ||
                        String.valueOf(tour.getEstimatedTime().toMinutes()).contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public TourLog saveTourLog(TourLog newTourLog) {
        if (newTourLog.getId() == null) {
            newTourLog.setId(UUID.randomUUID());
        } else {
            Optional<TourLog> entryOpt = tourLogs.stream()
                    .filter(log -> log.getId().equals(newTourLog.getId()))
                    .findAny();
            if (entryOpt.isPresent()) {
                TourLog log = entryOpt.get();
                log.setDateTime(newTourLog.getDateTime());
                log.setComment(newTourLog.getComment());
                log.setTotalDistanceM(newTourLog.getTotalDistanceM());
                log.setTotalTime(newTourLog.getTotalTime());
                log.setDifficulty(newTourLog.getDifficulty());
                log.setRating(newTourLog.getRating());
                return log;
            }
        }

        tourLogs.add(newTourLog);
        return newTourLog;
    }

    @Override
    public Optional<TourLog> findTourLogById(UUID id) {
        return tourLogs.stream()
                .filter(tourLog -> tourLog.getId().equals(id))
                .findAny();
    }

    @Override
    public List<TourLog> findTourLogsByTourId(UUID id) {
        return tourLogs.stream()
                .filter(log -> log.getTour().getId().equals(id))
                .collect(Collectors.toList());
    }
}
