package software.ulpgc.imageviewer;

import software.ulpgc.imageviewer.io.ImageDeserializer;
import software.ulpgc.imageviewer.model.Image;
import software.ulpgc.imageviewer.view.ImageDisplay;
import software.ulpgc.imageviewer.view.ViewPort;

import javax.swing.*;
import java.awt.*;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final ImageDeserializer deserializer;
    private Image image;

    public SwingImageDisplay(ImageDeserializer deserializer) {
        this.deserializer = deserializer;
        //this.addMouseListener();
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.revalidate();
        this.repaint();
    }

    @Override
    public Image currentDisplay() {
        return image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        drawImage(g);
    }

    private void drawImage(Graphics g) {
        java.awt.Image image = deserialize();
        ViewPort viewPort = ViewPort.ofSize(this.getWidth(), this.getHeight())
                .fit(image.getWidth(null), image.getHeight(null));
        g.drawImage(
                image,
                viewPort.xCoord(), viewPort.yCoord(),
                viewPort.width(), viewPort.height(),
                null
        );
    }

    public java.awt.Image deserialize() {
        return (java.awt.Image) deserializer.deserialize(image.content());
    }
}
