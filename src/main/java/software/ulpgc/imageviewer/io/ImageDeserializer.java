package software.ulpgc.imageviewer.io;

public interface ImageDeserializer {
    Object deserialize(byte[] bytes);
}
