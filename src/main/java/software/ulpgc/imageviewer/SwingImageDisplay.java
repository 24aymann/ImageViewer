package software.ulpgc.imageviewer;

import software.ulpgc.imageviewer.io.ImageDeserializer;
import software.ulpgc.imageviewer.model.Image;
import software.ulpgc.imageviewer.view.ImageDisplay;
import software.ulpgc.imageviewer.view.ViewPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final ImageDeserializer deserializer;
    private Image image;
    private double zoomFactor = 1.0;
    private int initialXCoordOffset = 0;
    private int xCoordOffset = 0;
    private int initialYCoordOffset = 0;
    private int yCoordOffset = 0;

    public SwingImageDisplay(ImageDeserializer deserializer) {
        this.deserializer = deserializer;
        this.setupListeners();
    }

    @Override
    public void show(Image image) {
        this.image = image;
        zoomFactor = 1.0;
        initialXCoordOffset = 0;
        xCoordOffset = 0;
        initialYCoordOffset = 0;
        yCoordOffset = 0;
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
        if (image == null) return;

        java.awt.Image awtImage = deserialize();
        ViewPort viewPort = ViewPort.ofSize(this.getWidth(), this.getHeight())
                .fit((int) (awtImage.getWidth(null) * zoomFactor),
                        (int) (awtImage.getHeight(null) * zoomFactor));

        g.drawImage(
                awtImage,
                viewPort.xCoord() + xCoordOffset, viewPort.yCoord() + yCoordOffset,
                (int) (viewPort.width() * zoomFactor), (int) (viewPort.height() * zoomFactor),
                null
        );
    }

    private void setupListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialXCoordOffset = e.getX();
                initialYCoordOffset = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                xCoordOffset += e.getX() - initialXCoordOffset;
                yCoordOffset += e.getY() - initialYCoordOffset;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                xCoordOffset += e.getX() - initialXCoordOffset;
                initialXCoordOffset = e.getX();

                yCoordOffset += e.getY() - initialYCoordOffset;
                initialYCoordOffset = e.getY();

                repaint();
            }
        });

        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                double prevZoomFactor = zoomFactor;

                if (e.getPreciseWheelRotation() < 0)
                    zoomFactor *= 1.1;
                else
                    zoomFactor /= 1.1;

                zoomFactor = Math.max(0.1, Math.min(zoomFactor, 10.0));

                xCoordOffset += (int) ((mouseX - xCoordOffset) * (1 - zoomFactor / prevZoomFactor));
                yCoordOffset += (int) ((mouseY - yCoordOffset) * (1 - zoomFactor / prevZoomFactor));
                repaint();
            }
        });
    }


    public java.awt.Image deserialize() {
        return (java.awt.Image) deserializer.deserialize(image.content());
    }
}