package software.ulpgc.imageviewer.swingApp;

import software.ulpgc.imageviewer.io.ImageDeserializer;
import software.ulpgc.imageviewer.view.ImageDisplay;
import software.ulpgc.imageviewer.view.ViewPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final List<PaintOrder> paintOrders = new ArrayList<>();
    private final ImageDeserializer deserializer;
    private double zoomFactor = 1.0;
    private int initialXCoordOffset = 0;
    private int xCoordOffset = 0;
    private int yCoordOffset = 0;
    private Shift shift = Shift.Null;
    private Release release = Release.Null;

    public SwingImageDisplay(ImageDeserializer deserializer) {
        this.deserializer = deserializer;
        this.setupListeners();
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Release release) {
        this.release = release != null ? release : Release.Null;
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public void paint(PaintOrder... paintOrders) {
        this.paintOrders.clear();
        Collections.addAll(this.paintOrders, paintOrders);
        resetZoom();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (PaintOrder paintOrder : paintOrders) {
            drawImage(g, paintOrder);
        }
    }

    private void drawImage(Graphics g, PaintOrder order) {
        BufferedImage image = deserialize(order.content());

        ViewPort viewPort = ViewPort.ofSize(this.getWidth(), this.getHeight())
                .fit((int) (image.getWidth(null) * zoomFactor),
                        (int) (image.getHeight(null) * zoomFactor));

        g.drawImage(
                image,
                viewPort.xCoord() + order.offset() + xCoordOffset, viewPort.yCoord() + yCoordOffset,
                (int) (viewPort.width() * zoomFactor), (int) (viewPort.height() * zoomFactor),
                null
        );
    }

    private void setupListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialXCoordOffset = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                release.offset(e.getX() - initialXCoordOffset);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                    resetZoom();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - initialXCoordOffset);
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

    private void resetZoom() {
        zoomFactor = 1.0;
        xCoordOffset = 0;
        yCoordOffset = 0;
        repaint();
    }

    private BufferedImage deserialize(byte[] content){
        return (BufferedImage) deserializer.deserialize(content);
    }
}