package software.ulpgc.imageviewer.control;

import software.ulpgc.imageviewer.view.ImageDisplay;

public class NextImageCommand implements Command {
    private final ImageDisplay display;

    public NextImageCommand(ImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.show(display.currentDisplay().next());
    }
}
