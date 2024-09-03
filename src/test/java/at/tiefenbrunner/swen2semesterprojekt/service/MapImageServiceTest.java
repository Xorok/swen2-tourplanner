package at.tiefenbrunner.swen2semesterprojekt.service;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("FieldCanBeLocal")
class MapImageServiceTest {

    private MapImageService mapImageService;

    @BeforeEach
    public void beforeEach() {
        mapImageService = new MapImageService();
    }

    @Test
    public void should_create_mapImage() throws Exception {
        String fileName = "test_mapImg";
        List<Point> route = List.of(
                new Point(-0.1278, 51.5074),  // London
                new Point(2.3522, 48.8566),   // Paris
                new Point(13.4050, 52.5200),  // Berlin
                new Point(12.4964, 41.9028)   // Rome
        );

        mapImageService.createRouteMap(route, fileName);

        // Asserts file was created and is not 0B
        Path path = Paths.get(Constants.MAP_IMAGES_PATH, fileName + ".png");
        assertTrue(Files.exists(path));
        assertTrue(Files.size(path) > 1);

        // Cleanup
        Files.delete(path);
    }
}