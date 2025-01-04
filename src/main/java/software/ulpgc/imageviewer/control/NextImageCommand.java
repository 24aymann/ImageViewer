package software.ulpgc.imageviewer.control;

public class NextImageCommand implements Command {
    private final ImagePresenter imagePresenter;

    public NextImageCommand(ImagePresenter imagePresenter) {
        this.imagePresenter = imagePresenter;
    }

    @Override
    public void execute() {
        imagePresenter.show(imagePresenter.getCurrentImage().next());
    }
}
