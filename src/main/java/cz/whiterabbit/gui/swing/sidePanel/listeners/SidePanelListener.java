package cz.whiterabbit.gui.swing.sidePanel.listeners;

public interface SidePanelListener {
    void onHistoryClicked();
    void onInfoClicked();
    void onSettingsClicked();
    void onSaveClicked();
    void onLoadClicked();
    void onPreviewPressed(int previewIndex);
    void onPreviewReleased(int previewIndex);
    void onReverseClicked(int reverseIndex);
    void onSaveFileClicked(String fileName);
    void onLoadFileClicked(String filename);
    void onSettingsConfirmed();
    void onNewGameClicked();
    void onSuggestMoveClicked();
    void onRulesClicked();
}
