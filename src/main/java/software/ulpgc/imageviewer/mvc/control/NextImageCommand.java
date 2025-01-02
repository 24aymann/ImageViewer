package software.ulpgc.imageviewer.mvc.control;

import software.ulpgc.imageviewer.mvc.view.ImageDisplay;

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
