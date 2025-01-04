package software.ulpgc.imageviewer.control;

import software.ulpgc.imageviewer.model.Image;
import software.ulpgc.imageviewer.view.ImageDisplay;

public class ImagePresenter {
    private Image image;
    private final ImageDisplay imageDisplay;

    public ImagePresenter(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
        this.imageDisplay.on(shift());
        this.imageDisplay.on(release());
    }

    public void show(Image image) {
        this.image = image;
        updateDisplay();
    }

    public Image getCurrentImage() {
        return image;
    }

    private void updateDisplay() {
        imageDisplay.paint(repaintDisplayForCurrentImageWith(0));
    }

    private ImageDisplay.PaintOrder repaintDisplayForCurrentImageWith(int offset) {
        return new ImageDisplay.PaintOrder(image.content(), offset, image);
    }

    private ImageDisplay.PaintOrder repaintDisplayForAdjacentImageWith(int offset, Image adjacentImage) {
        return new ImageDisplay.PaintOrder(adjacentImage.content(), offset, adjacentImage);
    }

    private ImageDisplay.Shift shift() {
        return offset -> imageDisplay.paint(
                repaintDisplayForCurrentImageWith(offset),
                isShiftingToPreviousImage(offset)
                        ? repaintDisplayForAdjacentImageWith(offset - imageDisplay.width(), image.previous())
                        : repaintDisplayForAdjacentImageWith(imageDisplay.width() + offset, image.next())
        );
    }

    public ImageDisplay.Release release() {
        return offset -> {
            updateCurrentImage(offset);
            updateDisplay();
        };
    }

    private void updateCurrentImage(int offset) {
        if (isCenteredOnCurrentImage(offset)) return;
        image = isShiftingToPreviousImage(offset)
                ? image.previous()
                : image.next();
    }

    private boolean isCenteredOnCurrentImage(int offset) {
        return Math.abs(offset) <= imageDisplay.width() / 2;
    }

    private boolean isShiftingToPreviousImage(int offset) {
        return offset > 0;
    }
}
