package nl.han.toetsplatform.module.voorbereiden.data;




import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlLoader {

    private final static Logger LOGGER = Logger.getLogger(SqlLoader.class.getName());


    /** Loads the sql from a .sql file in resources
     * @param path the path of the sql file
     * @return a string containing the content of the file
     */
    public String load(String path) {
        String resourceName = "sql" + "/" + path + ".sql"; // could also be addVraag constant

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            StringBuilder stringBuilder = new StringBuilder();

            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            InputStreamReader streamReader = new InputStreamReader(resourceStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null; ) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Could not find " + path + ".sql file");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading file");
        }
        return "ERROR: NO SQL FILE FOUND";
    }
}
