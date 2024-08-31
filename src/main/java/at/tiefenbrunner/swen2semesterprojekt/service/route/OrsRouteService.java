package at.tiefenbrunner.swen2semesterprojekt.service.route;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Point;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.function.Consumer;

@Log4j2
public class OrsRouteService {
    private static final String BASE_URL = "https://api.openrouteservice.org/v2/directions/";
    private static final String DIRECTIONS_URL = "https://api.openrouteservice.org/v2/directions/%s?api_key=%s&start=%f,%f&end=%f,%f";

    private final ConfigService configService;

    public OrsRouteService(ConfigService configService) {
        this.configService = configService;
    }

    public void getRoute(Point start, Point end, OrsProfile profile, Consumer<JSONObject> callback) {
        String apiKey = configService.getConfigValue("ors.api-key");

        String urlString = String.format(
                Locale.US,
                DIRECTIONS_URL,
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

                JSONObject route = new JSONObject(content.toString());
                callback.accept(route);
            } catch (Exception e) {
                log.error("An exception occurred while fetching the route!", e);
                // TODO: Handle error
            }
        }).start();
    }
}