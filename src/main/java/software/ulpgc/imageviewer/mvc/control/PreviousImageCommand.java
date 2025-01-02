package software.ulpgc.imageviewer.mvc.control;

import software.ulpgc.imageviewer.mvc.view.ImageDisplay;

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
