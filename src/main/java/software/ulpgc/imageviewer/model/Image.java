package software.ulpgc.imageviewer.model;

public interface Image {
    String name();
    byte[] content();
    Image previous();
    Image next();
    Format format();

    enum Format {
        JPG, JPEG, PNG, GIF
    }
}
