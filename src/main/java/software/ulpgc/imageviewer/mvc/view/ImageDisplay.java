package software.ulpgc.imageviewer.mvc.view;

import software.ulpgc.imageviewer.mvc.model.Image;

public interface ImageDisplay {
    void show(Image image);
    Image currentDisplay();
}
