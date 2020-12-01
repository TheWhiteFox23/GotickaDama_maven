package cz.whiterabbit.gui;

import java.io.IOException;

public interface GUIFrame {
    void drawFrame() throws InterruptedException, IOException;
    void setFrameListener(FrameListener frameListener);
}
