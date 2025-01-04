package software.ulpgc.imageviewer;

import software.ulpgc.imageviewer.control.ImagePresenter;
import software.ulpgc.imageviewer.control.NextImageCommand;
import software.ulpgc.imageviewer.control.PreviousImageCommand;
import software.ulpgc.imageviewer.io.FileImageLoader;
import software.ulpgc.imageviewer.model.Image;
import software.ulpgc.imageviewer.swingApp.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        Image firstImage = new FileImageLoader().load();
        ImagePresenter presenter = new ImagePresenter(mainFrame.getImageDisplay());
        mainFrame
                .add("←", new PreviousImageCommand(presenter))
                .add("→", new NextImageCommand(presenter));
        presenter.show(firstImage);
        mainFrame.setVisible(true);
    }
}
