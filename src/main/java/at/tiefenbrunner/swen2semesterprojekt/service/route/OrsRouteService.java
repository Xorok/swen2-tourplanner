package at.tiefenbrunner.swen2semesterprojekt.service.route;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import javafx.application.Platform;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@Log4j2
public class OrsRouteService {
    private static final String BASE_URL = "https://api.openrouteservice.org/v2/";
    private static final String DIRECTIONS_URL_TEMPLATE = BASE_URL + "directions/%s?api_key=%s&start=%f,%f&end=%f,%f";

    private final ConfigService configService;

    public OrsRouteService(ConfigService configService) {
        this.configService = configService;
    }

    public void getRoute(Point start, Point end, OrsProfile profile, Consumer<List<Point>> successCallback, Consumer<String> errorCallback) {
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
                List<Point> route = processJsonData(response);

                // Callback UI thread with data
                Platform.runLater(() -> successCallback.accept(route));
            } catch (Exception e) {
                log.error("An exception occurred while fetching the route!", e);
                // Callback UI thread with data
                Platform.runLater(() -> errorCallback.accept("There is no route available for the selected configuration!"));
                // TODO: Add different error messages based on exception type (e.g. internet issues, etc)
            }
        }).start();
    }

    private List<Point> processJsonData(JSONObject response) {
        JSONArray coordinates = response.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");

        List<Point> points = new ArrayList<>(coordinates.length());
        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coord = coordinates.getJSONArray(i);
            double lng = coord.getDouble(0);
            double lat = coord.getDouble(1);
            points.add(new Point(lng, lat));
        }

        return points;
    }
}