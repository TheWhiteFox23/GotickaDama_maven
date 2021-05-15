package cz.whiterabbit.gui.swing.sidePanel.listeners;

public interface HistoryPanelListener {
    void onPreviewPressed(int previewIndex);
    void onPreviewReleased(int previewIndex);
    void onReverseClicked(int reverseIndex);
}
