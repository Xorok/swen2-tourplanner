package at.tiefenbrunner.swen2semesterprojekt.data.service;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.TourRepository;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourPoint;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
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
    private final TourReportService tourReportService;
    private final JsonExportImportService jsonExportImportService;

    public TourService(TourRepository tourRepository, MapImageService mapImageService, TourReportService tourReportService, JsonExportImportService jsonExportImportService) {
        this.tourRepository = tourRepository;
        this.mapImageService = mapImageService;
        this.tourReportService = tourReportService;
        this.jsonExportImportService = jsonExportImportService;
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.saveTour(tour);
    }

    public void saveAllTours(List<Tour> tour) {
        tourRepository.insertAllTours(tour);
    }

    public void deleteTour(UUID id) {
        tourRepository.deleteTour(id);
        mapImageService.deleteRouteMap(id.toString());
    }

    public void deleteAllTours() {
        tourRepository.deleteAllTours();
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

    public void generateReport(Tour tour) {
        if (tour == null || tour.getId() == null)
            return;

        try {
            tourReportService.generateReport(List.of(tour), "tour_report_" + tour.getId(), true);
        } catch (Exception e) {
            log.error("Couldn't generate report for tour {}!", tour.getId(), e);
            // TODO: Show error in UI
        }
    }

    public void generateSummaryReport() {
        try {
            tourReportService.generateReport(findAllTours(), "tours_summary_report", false);
        } catch (Exception e) {
            log.error("Couldn't generate summary report for tours!", e);
            // TODO: Show error in UI
        }
    }

    public void generateJsonBackup() {
        try {
            jsonExportImportService.exportToursToJson(findAllTours(), "all_tours_export");
        } catch (IOException e) {
            log.error("Couldn't generate JSON export for tours!", e);
            // TODO: Show error in UI
        }
    }

    public void importJsonBackup() {
        try {
            List<Tour> tours = jsonExportImportService.importToursFromJson(Constants.EXPORTS_PATH + "all_tours_export.json");
            deleteAllTours();
            saveAllTours(tours);
        } catch (IOException e) {
            log.error("Couldn't import JSON Backup of tours!", e);
            // TODO: Show error in UI
        }
    }
}
