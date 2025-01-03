package software.ulpgc.imageviewer;

import software.ulpgc.imageviewer.control.NextImageCommand;
import software.ulpgc.imageviewer.control.PreviousImageCommand;
import software.ulpgc.imageviewer.io.FileImageLoader;
import software.ulpgc.imageviewer.model.Image;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = MainFrame.create();
        mainFrame
                .initWith(firstImage())
                .add("←", new PreviousImageCommand(mainFrame.getImageDisplay()))
                .add("→", new NextImageCommand(mainFrame.getImageDisplay()))
                .setVisible(true);
    }

    private static Image firstImage() {
        return new FileImageLoader().load();
    }
}
