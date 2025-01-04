package software.ulpgc.imageviewer.swingApp;

import software.ulpgc.imageviewer.io.ImageDeserializer;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SwingImageDeserializer implements ImageDeserializer {
    @Override
    public Object deserialize(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }
}
