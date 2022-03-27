package res;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Class for loading game resources
 */
public class ResourceLoader {
    public static InputStream loadResource(String resName){
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
    }
    
    /**
     * Loads an image from a path
     * @param resName the location of the image to load
     */
    public static Image loadImage(String resName) throws IOException{
        URL url = ResourceLoader.class.getClassLoader().getResource(resName);
        return ImageIO.read(url);
    }
}
