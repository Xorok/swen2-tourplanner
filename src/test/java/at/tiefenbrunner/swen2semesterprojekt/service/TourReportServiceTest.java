package at.tiefenbrunner.swen2semesterprojekt.service;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourType;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("FieldCanBeLocal")
class TourReportServiceTest {

    private TourReportService tourReportService;

    @BeforeEach
    public void beforeEach() {
        tourReportService = new TourReportService();
    }

    @Test
    public void should_create_report() throws Exception {
        String fileName = "test_report";
        List<Tour> tours = List.of(
                new Tour(
                        UUID.randomUUID(),
                        "Name 1",
                        "Desc 1",
                        TourType.VACATION,
                        342760,
                        Duration.ofHours(1).plus(20, ChronoUnit.MINUTES),
                        List.of(),
                        null
                ),
                new Tour(
                        UUID.randomUUID(),
                        "Name 2",
                        "Desc 2",
                        TourType.HIKE,
                        4500,
                        Duration.ofHours(4).plus(30, ChronoUnit.MINUTES),
                        List.of(),
                        null
                )
        );

        tourReportService.generateReport(tours, fileName, false);

        // Asserts file was created and is not 0B
        Path path = Paths.get(Constants.REPORTS_PATH, fileName + ".pdf");
        assertTrue(Files.exists(path));
        assertTrue(Files.size(path) > 1);

        // Cleanup
        Files.delete(path);
    }
}