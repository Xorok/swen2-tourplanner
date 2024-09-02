package at.tiefenbrunner.swen2semesterprojekt.service.route;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static at.tiefenbrunner.swen2semesterprojekt.util.DurationDecimalTimeConverter.fromDecimalSeconds;

@Log4j2
public class OrsRouteService {
    private static final String BASE_URL = "https://api.openrouteservice.org/v2/";
    private static final String DIRECTIONS_URL_TEMPLATE = BASE_URL + "directions/%s?api_key=%s&start=%f,%f&end=%f,%f";

    public static void main(String[] args) {
        Point start = new Point(-0.1278, 51.5074);  // London
        Point end = new Point(2.3522, 48.8566);   // Paris

        try {
            OrsRouteService orsRouteService = new OrsRouteService(new ConfigService(Constants.CONFIG_FILE_PATH));
            orsRouteService.getRoute(start, end, OrsProfile.DRIVING_CAR,
                    log::info,
                    log::error
            );
        } catch (IOException e) {
            log.error("Error getting route: {}", e.getMessage());
        }
    }

    private final ConfigService configService;

    public OrsRouteService(ConfigService configService) {
        this.configService = configService;
    }

    public void getRoute(Point start, Point end, OrsProfile profile, Consumer<RouteResult> successCallback, Consumer<String> errorCallback) {
        String apiKey = configService.getConfigValue("ors.api-key");

        String urlString = String.format(
                Locale.US,
                DIRECTIONS_URL_TEMPLATE,
                profile.toString(),
                apiKey,
                start.getX(), start.getY(),
                end.getX(), end.getY()
        );

        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();

                JSONObject response = new JSONObject(content.toString());
                RouteResult route = processJsonData(response);

                // Callback UI thread with data
                successCallback.accept(route);
            } catch (Exception e) {
                log.error("An exception occurred while fetching the route!", e);
                // Callback UI thread with data
                errorCallback.accept("There is no route available for the selected configuration!");
                // TODO: Add different error messages based on exception type (e.g. internet issues, etc)
            }
        }).start();
    }

    private RouteResult processJsonData(JSONObject response) {
        log.info("Route Response: {}", response);

        JSONObject container = response.getJSONArray("features")
                .getJSONObject(0);

        JSONArray coordinates = container
                .getJSONObject("geometry")
                .getJSONArray("coordinates");

        List<Point> points = new ArrayList<>(coordinates.length());
        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coord = coordinates.getJSONArray(i);
            double lng = coord.getDouble(0);
            double lat = coord.getDouble(1);
            points.add(new Point(lng, lat));
        }

        JSONObject routeProperties = container
                .getJSONObject("properties")
                .getJSONObject("summary");

        Duration duration = fromDecimalSeconds(routeProperties.getDouble("duration"));
        int distanceInM = (int) Math.round(routeProperties.getDouble("distance"));

        return new RouteResult(points, duration, distanceInM);
    }
}