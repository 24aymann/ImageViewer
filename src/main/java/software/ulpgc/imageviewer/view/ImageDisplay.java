package software.ulpgc.imageviewer.view;

import software.ulpgc.imageviewer.model.Image;

public interface ImageDisplay {
    void show(Image image);
    Image currentDisplay();
}
