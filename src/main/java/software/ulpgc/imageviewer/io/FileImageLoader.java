package software.ulpgc.imageviewer.io;

import software.ulpgc.imageviewer.model.Image;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class FileImageLoader implements ImageLoader {
    private final File[] files;

    public FileImageLoader() {
        try {
            URL imagesFolder = requireNonNull(getClass().getClassLoader().getResource("images"));

            File folder = new File(imagesFolder.toURI());
            if (!folder.isDirectory())
                throw new IllegalArgumentException(folder.getPath() + " directory is empty.");

            this.files = requireNonNull(folder.listFiles(ofImageFiles()));
        }
        catch (Exception e) {throw new RuntimeException(e);}
    }

    private FileFilter ofImageFiles() {
        return file -> validImageExtensions()
                .anyMatch(e-> validFileExtensions(file, e));
    }

    private Stream<String> validImageExtensions() {
        return Arrays.stream(Image.Format.values())
                .map(s -> s.name().toLowerCase());
    }

    private boolean validFileExtensions(File file, String extension) {
        return file.getName().toLowerCase().endsWith(extension);
    }

    @Override
    public Image load() {
        return getImageAt(0);
    }

    private Image getImageAt(int index) {
        return new Image() {
            @Override
            public byte[] content() {
                try {
                    return Files.readAllBytes(currentFile().toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private File currentFile() {
                return files[index];
            }

            @Override
            public Image next() {
                return getImageAt(nextIndex());
            }

            private int nextIndex() {
                return (index + 1) % files.length;
            }

            @Override
            public Image previous() {
                return getImageAt(previousIndex());
            }

            private int previousIndex() {
                return index > 0 ? index - 1 : files.length - 1;
            }
        };
    }
}
