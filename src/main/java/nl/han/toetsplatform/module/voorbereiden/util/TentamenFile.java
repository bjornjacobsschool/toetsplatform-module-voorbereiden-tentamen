package nl.han.toetsplatform.module.voorbereiden.util;

import com.google.gson.Gson;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.nio.charset.StandardCharsets.UTF_8;

public class TentamenFile {
    private Gson _gson;

    @Inject
    public TentamenFile(Gson gson)
    {
        _gson = gson;
    }

    //todo: moet nog test voor geschreven worden
    public <T> void exportToFile(T object, File directory) throws IOException {
        if(directory != null) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmmss");

            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(new FileOutputStream(directory.getPath()+"\\Export Toetsapplicatie "+dateFormat.format(date)+".json"));
                String json = _gson.toJson(object);
                out.write(json.getBytes(UTF_8));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public <T> void exportToFile(T object, Window window) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(window);

        exportToFile(object, selectedDirectory);
    }

}
