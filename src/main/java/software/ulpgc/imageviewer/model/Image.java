package software.ulpgc.imageviewer.model;

public interface Image {
    byte[] content();
    Image previous();
    Image next();

    enum Format {
        JPG, JPEG, PNG, GIF
    }
}
