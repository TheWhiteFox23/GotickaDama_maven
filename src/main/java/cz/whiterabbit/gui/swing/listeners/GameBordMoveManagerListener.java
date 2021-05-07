package cz.whiterabbit.gui.swing.listeners;

public interface GameBordMoveManagerListener {
    void onMove(byte[] move);
    void onRepaintCall();
}
