package software.ulpgc.imageviewer.view;

import software.ulpgc.imageviewer.model.Image;

public interface ImageDisplay {
    void on(Shift shift);
    void on(Release release);

    interface Shift {
        Shift Null = _ -> {};
        void offset(int offset);
    }

    interface Release {
        Release Null = _ -> {};
        void offset(int offset);
    }

    void paint(PaintOrder... paintOrders);
    record PaintOrder(byte[] content, int offset, Image image) {}

    int width();
}
