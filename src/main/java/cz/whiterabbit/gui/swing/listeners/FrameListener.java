package cz.whiterabbit.gui.swing.listeners;

public interface FrameListener {
    //BottomPanel
    void onConfirmClicked();
    void onRedoClicked();
    void onUndoClicked();

    //SidePanel
    void onHistoryClicked();
    void onInfoClicked();
    void onSettingsClicked();
    void onSaveClicked();
    void onLoadClicked();

    //PlayBoard
    void onFieldClicked(int field);
    void onFieldSelected(int field);
}
