package at.tiefenbrunner.swen2semesterprojekt.service;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourPoint;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.service.osm.MapImageService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourService {

    private final TourRepository tourRepository;
    private final MapImageService mapImageService;

    public TourService(TourRepository tourRepository, MapImageService mapImageService) {
        this.tourRepository = tourRepository;
        this.mapImageService = mapImageService;
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.saveTour(tour);
    }

    public void deleteTour(UUID id) {
        tourRepository.deleteTour(id);
        mapImageService.deleteRouteMap(id.toString());
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

    public void saveRoute(List<Point> route, Tour tour) {
        // Save image of route
        saveRouteImage(route, tour);

        List<TourPoint> tourPoints = new ArrayList<>(route.size());
        for (int i = 0; i < route.size(); i++) {
            tourPoints.add(
                    new TourPoint(
                            null,
                            tour,
                            BigInteger.valueOf(i),
                            route.get(i)
                    ));
        }
        // Save route points in database
        tourRepository.saveTourPoints(tourPoints);
    }

    private void saveRouteImage(List<Point> route, Tour tour) {
        try {
            mapImageService.createRouteMap(route, tour.getId().toString());
        } catch (IOException e) {
            log.error("Couldn't get or save route image!", e);
            // TODO: Handle error - invalid entry with no map img!
        }
    }

    public List<Point> findRouteByTourId(UUID id) {
        return tourRepository.findRouteByTourId(id).stream()
                .map(TourPoint::getPoint).toList();
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
