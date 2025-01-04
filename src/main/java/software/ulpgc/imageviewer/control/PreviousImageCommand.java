package software.ulpgc.imageviewer.control;

public class PreviousImageCommand implements Command {
    private final ImagePresenter imagePresenter;

    public PreviousImageCommand(ImagePresenter imagePresenter) {
        this.imagePresenter = imagePresenter;
    }

    @Override
    public void execute() {
        imagePresenter.show(imagePresenter.getCurrentImage().previous());
    }
}
