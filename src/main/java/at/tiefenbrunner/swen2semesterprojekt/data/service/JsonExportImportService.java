package at.tiefenbrunner.swen2semesterprojekt.data.service;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonExportImportService {

    private final ObjectMapper objectMapper;

    public JsonExportImportService() {
        objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    public void exportToursToJson(List<Tour> tours, String filePath) throws IOException {
        //noinspection ConstantValue
        if (!Constants.EXPORTS_PATH.isEmpty()) {
            Files.createDirectories(Paths.get(Constants.EXPORTS_PATH));
        }

        objectMapper.writeValue(new File(Constants.EXPORTS_PATH + filePath + ".json"), tours);
    }

    public List<Tour> importToursFromJson(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Tour.class));
    }
}