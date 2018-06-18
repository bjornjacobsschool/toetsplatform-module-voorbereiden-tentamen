package nl.han.toetsplatform.module.voorbereiden.data.export;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.shared.plugin.VraagView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TentamenPdfGenerator {

    public void generatePdf(SamengesteldTentamenDto tentamen) {
        try {
            PDDocument document = new PDDocument();

            PDPage my_page = new PDPage();

            PDPageContentStream contentStream = new PDPageContentStream(document, my_page);

            writeText("Naam: " + tentamen.getNaam(), 25 , 700, contentStream);
            writeText("Versie: " + tentamen.getVersie().getNummer(), 25 , 675, contentStream);
            writeText("Aantal vragen: " + tentamen.getVragen().size(), 25 , 650, contentStream);

            contentStream.close();
            document.addPage(my_page);

            for(VragenbankVraagDto vraag : tentamen.getVragen()){
                PDPage page = new PDPage();

                PDImageXObject xImage = getVraagImage(document, vraag);

                PDPageContentStream pageContentStream = new PDPageContentStream(document, page);

                pageContentStream.beginText();
                pageContentStream.newLineAtOffset(25, 700 );
                pageContentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                pageContentStream.showText(vraag.getNaam() + " (" + vraag.getPunten() + ")");

                pageContentStream.endText();

                pageContentStream.drawImage(xImage, 25, 650 - xImage.getHeight());
                pageContentStream.close();

                document.addPage(page);

            }

            document.save("./" + tentamen.getNaam() + tentamen.getVersie().getNummer() + ".pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeText(String text, float x, float y, PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.showText(text);
        contentStream.endText();
    }

    private PDImageXObject getVraagImage(PDDocument document, VragenbankVraagDto vraag) throws ClassNotFoundException, IOException {
        Plugin plugin = PluginLoader.getPlugin(vraag.getVraagtype());
        VraagView vraagView = plugin.getVraagView(vraag.getVraagData());
        Node vraagNode =  vraagView.getView();
        Node antwoordNode = plugin.getAntwoordView(vraag.getVraagData()).getView();
        VBox vbox = new VBox();
        vbox.getChildren().add(vraagNode);
        Pane pane = new Pane();
        pane.setPrefHeight(50);
        vbox.getChildren().add(pane);
        vbox.getChildren().add(new Label("Antwoord:"));
        vbox.getChildren().add(antwoordNode);
        Scene scene = new Scene((Parent)vbox);
        Stage stage = new Stage();
        vbox.setStyle("-fx-background: #FFFFFF;");
        stage.setScene(scene);
        stage.show();

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setViewport(new Rectangle2D(0,0, 500, 500));

        WritableImage writableImage = vbox.snapshot(snapshotParameters, null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        PDImageXObject xImage = JPEGFactory.createFromImage(document, bufferedImage);
        stage.close();
        return xImage;
    }
}
