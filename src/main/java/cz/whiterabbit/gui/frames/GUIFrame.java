package cz.whiterabbit.gui.frames;

import cz.whiterabbit.gui.listeners.FrameListener;

import java.io.IOException;

public interface GUIFrame {
    void drawFrame() throws InterruptedException, IOException;
    void setFrameListener(FrameListener frameListener);
}
