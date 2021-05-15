package cz.whiterabbit.gui.swing.listeners;

public interface FrameListener {
    //BottomPanel
    void onConfirmClicked();
    void onRedoClicked();
    void onUndoClicked();

    //SidePanel
    void onSaveFileClicked(String filename);
    void onLoadFileClicked(String filename);
    void onSettingsConfirm();
    void onNewGameClicked();
    void onSuggestMoveClicked();
    void onRulesClicked();

    //PlayBoard
    void onFieldClicked(int field);
    void onFieldSelected(int field);

    //HistoryPanel
    void onPreviewPressed(int previewIndex);
    void onPreviewReleased(int previewIndex);
    void onReverseClicked(int reverseIndex);


}
