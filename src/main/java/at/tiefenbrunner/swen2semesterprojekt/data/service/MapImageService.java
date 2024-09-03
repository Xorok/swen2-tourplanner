package at.tiefenbrunner.swen2semesterprojekt.data.service;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class MapImageService {
    private static final String TILE_URL_TEMPLATE = "https://tile.openstreetmap.org/%d/%d/%d.png";
    private static final int TILE_SIZE = 256;

    public static void main(String[] args) {
        // Example route: London -> Paris -> Berlin -> Rome
        List<Point> route = Arrays.asList(
                new Point(-0.1278, 51.5074),  // London
                new Point(2.3522, 48.8566),   // Paris
                new Point(13.4050, 52.5200),  // Berlin
                new Point(12.4964, 41.9028)   // Rome
        );

        try {
            MapImageService mapImageService = new MapImageService();
            mapImageService.createRouteMap(route, "europe_route_map");
            log.info("Map created successfully! Check 'europe_route_map.png' in the project directory.");
        } catch (IOException e) {
            log.error("Error creating map: {}", e.getMessage());
        }
    }

    public void createRouteMap(List<Point> route, String fileName) throws IOException {
        if (route.size() < 2) {
            throw new IllegalArgumentException("Route must have at least two Points");
        }

        // Calculate bounding box
        double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE, maxLon = -Double.MAX_VALUE;
        for (Point coord : route) {
            minLat = Math.min(minLat, coord.getY());
            maxLat = Math.max(maxLat, coord.getY());
            minLon = Math.min(minLon, coord.getX());
            maxLon = Math.max(maxLon, coord.getX());
        }

        // Calculate zoom level and tile Points
        int zoom = calculateZoom(minLat, maxLat, minLon, maxLon);
        int minX = lonToTileX(minLon, zoom);
        int maxX = lonToTileX(maxLon, zoom);
        int minY = latToTileY(maxLat, zoom);
        int maxY = latToTileY(minLat, zoom);

        // Create combined image
        int width = (maxX - minX + 1) * TILE_SIZE;
        int height = (maxY - minY + 1) * TILE_SIZE;
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = combinedImage.createGraphics();

        // Download and draw tiles
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                BufferedImage tile = downloadTile(zoom, x, y);
                g.drawImage(tile, (x - minX) * TILE_SIZE, (y - minY) * TILE_SIZE, null);
            }
        }

        // Draw route
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < route.size() - 1; i++) {
            Point start = route.get(i);
            Point end = route.get(i + 1);
            int x1 = (int) ((lonToTileX(start.getX(), zoom) - minX) * TILE_SIZE + (longitudeToPixelX(start.getX(), zoom) % TILE_SIZE));
            int y1 = (int) ((latToTileY(start.getY(), zoom) - minY) * TILE_SIZE + (latitudeToPixelY(start.getY(), zoom) % TILE_SIZE));
            int x2 = (int) ((lonToTileX(end.getX(), zoom) - minX) * TILE_SIZE + (longitudeToPixelX(end.getX(), zoom) % TILE_SIZE));
            int y2 = (int) ((latToTileY(end.getY(), zoom) - minY) * TILE_SIZE + (latitudeToPixelY(end.getY(), zoom) % TILE_SIZE));
            g.drawLine(x1, y1, x2, y2);
        }

        g.dispose();

        // Create map images folder if it doesn't exist already
        //noinspection ConstantValue
        if (!Constants.MAP_IMAGES_PATH.isEmpty()) {
            Files.createDirectories(Paths.get(Constants.MAP_IMAGES_PATH));
        }

        // Save image
        ImageIO.write(combinedImage, "png", new File(Constants.MAP_IMAGES_PATH + fileName + ".png"));
    }

    public void deleteRouteMap(String fileName) {
        File mapImg = new File(Constants.MAP_IMAGES_PATH + fileName + ".png");
        if (mapImg.delete()) {
            log.info("Deleted the file: {}", mapImg.getName());
        } else {
            log.error("Failed to delete the file.");
            // TODO: Handle error
        }
    }

    private static BufferedImage downloadTile(int zoom, int x, int y) throws IOException {
        String tileUrl = String.format(TILE_URL_TEMPLATE, zoom, x, y);
        URL url = new URL(tileUrl); // TODO: Fix deprecation
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", Constants.USER_AGENT);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(connection.getInputStream().readAllBytes())) {
            return ImageIO.read(bis);
        }
    }

    private static int calculateZoom(double minLat, double maxLat, double minLon, double maxLon) {
        double latDiff = maxLat - minLat;
        double lonDiff = maxLon - minLon;
        double maxDiff = Math.max(latDiff, lonDiff);
        return (int) Math.floor(Math.log(360 / maxDiff) / Math.log(2));
    }

    private static int lonToTileX(double lon, int zoom) {
        return (int) Math.floor((lon + 180) / 360 * (1 << zoom));
    }

    private static int latToTileY(double lat, int zoom) {
        return (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));
    }

    private static double longitudeToPixelX(double lon, int zoom) {
        return (lon + 180) / 360 * (1 << zoom) * TILE_SIZE;
    }

    private static double latitudeToPixelY(double lat, int zoom) {
        return (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom) * TILE_SIZE;
    }
}