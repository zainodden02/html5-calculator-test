package functions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static final Logger logger = LogManager.getLogger();

    public static JSONObject loadJSON(String filename) {
        JSONObject object = null;
        try {
            filename = addJSONExtension(filename, ".json");
            File file = new File("src\\main\\java\\data\\" + filename);
            object = new JSONObject(Files.readFile(file));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return object;
    }

    /**
     * Add a file extension if missing.
     *
     * @param filename  The filename to validate.
     * @param extension The expected extension.
     * @return The filename with its extension.
     */
    private static String addJSONExtension(String filename, String extension) {
        if (!filename.toLowerCase().endsWith(extension)) {
            return filename + extension;
        }
        return filename;
    }
}
