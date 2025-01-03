package software.ulpgc.imageviewer.control;

import software.ulpgc.imageviewer.view.ImageDisplay;

public class PreviousImageCommand implements Command {
    private final ImageDisplay display;

    public PreviousImageCommand(ImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.show(display.currentDisplay().previous());
    }
}
