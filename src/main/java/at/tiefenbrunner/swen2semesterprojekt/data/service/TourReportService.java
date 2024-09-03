package at.tiefenbrunner.swen2semesterprojekt.data.service;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import at.tiefenbrunner.swen2semesterprojekt.util.DurationFormat;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
public class TourReportService {

    public void generateReport(List<Tour> tours, String fileName, boolean withMapImages) throws Exception {
        // Create reports folder if it doesn't exist already
        //noinspection ConstantValue
        if (!Constants.REPORTS_PATH.isEmpty()) {
            Files.createDirectories(Paths.get(Constants.REPORTS_PATH));
        }

        try (Document document = new Document(new PdfDocument(new PdfWriter(Constants.REPORTS_PATH + fileName + ".pdf")))) {
            Style titleStyle = new Style()
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            Style paragraphStyle = new Style()
                    .setFontSize(14)
                    .setBold()
                    .setItalic();
            Style columnStyle = new Style()
                    .setFontSize(12)
                    .setBold();

            document.add(new Paragraph("Tour" + (tours.size() > 1 ? "s Summary" : "") + " Report").addStyle(titleStyle));

            DurationFormat df = new DurationFormat();
            DateTimeFormatter dtf = DateTimeFormatter
                    .ofPattern(Constants.DATE_TIME_FORMAT)
                    .withZone(ZoneId.systemDefault());
            for (Tour tour : tours) {
                document.add(new Paragraph(new Text("Tour: " + tour.getName()).addStyle(paragraphStyle)));

                if (withMapImages) {
                    try {
                        Image image = new Image(ImageDataFactory.create(Constants.MAP_IMAGES_PATH + tour.getId().toString() + ".png"));
                        document.add(image);
                    } catch (IOException e) {
                        log.error("No image available for tour {}!", tour.getId().toString(), e);
                    }
                }

                document.add(new Paragraph(new Text("Description: ").addStyle(columnStyle)).add(tour.getDescription()));
                document.add(new Paragraph(new Text("Type: ").addStyle(columnStyle)).add(tour.getTourType().toString()));
                document.add(new Paragraph(new Text("Duration: ").addStyle(columnStyle)).add(df.format(tour.getEstimatedTime())));
                document.add(new Paragraph(new Text("Distance: ").addStyle(columnStyle)).add(tour.getDistanceM() + "m"));

                if (!tour.getTourLogs().isEmpty()) {
                    document.add(new Paragraph(new Text("Tour Logs:")).addStyle(columnStyle));
                    Table table = new Table(3);
                    table.addCell("Timestamp");
                    table.addCell("Comment");
                    table.addCell("Total Distance");
                    table.addCell("Total Time");
                    table.addCell("Difficulty");
                    table.addCell("Rating");

                    for (TourLog log : tour.getTourLogs()) {
                        table.addCell(dtf.format(log.getDateTime()));
                        table.addCell(log.getComment());
                        table.addCell(log.getTotalDistanceM() + "m");
                        table.addCell(df.format(log.getTotalTime()));
                        table.addCell(log.getDifficulty().toString());
                        table.addCell(log.getRating() + "/100");
                    }
                    document.add(table);
                }

                document.add(new Paragraph("\n"));
            }
        }
    }
}