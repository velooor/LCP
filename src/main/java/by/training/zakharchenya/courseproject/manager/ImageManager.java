package by.training.zakharchenya.courseproject.manager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class ImageManager {

    private static final Logger LOG = LogManager.getLogger();

    private static final Path FILE_PATH_AVATAR = Paths.get("images", "default_account_avatarr.png");
    private byte[] defaultAvatar = {};

    private static ImageManager imageManager;
    private static boolean init = false;

    private ImageManager() {
    }

    private static void init(){
        imageManager = new ImageManager();
        ClassLoader classLoader = imageManager.getClass().getClassLoader();
        URL url = classLoader.getResource(FILE_PATH_AVATAR.toString());
        if (url != null) {
            File file = new File(url.getFile());
            if (file.exists()) {
                try {
                    imageManager.defaultAvatar = Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    LOG.log(Level.ERROR, "Can't load account default images.");
                }
            } else {
                LOG.log(Level.ERROR, "Can't load account default images.");
            }
        } else {
            LOG.log(Level.ERROR, "Can't load account default images.");
        }
        init = true;
    }

    public static byte[] getDefaultAvatar() {
        if (!init) {
            init();
        }
        return imageManager.defaultAvatar;
    }
}
