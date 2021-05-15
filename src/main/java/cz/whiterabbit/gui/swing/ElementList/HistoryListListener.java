package cz.whiterabbit.gui.swing.ElementList;

public interface HistoryListListener {
    void onPreviewPressed(int previewIndex);
    void onPreviewReleased(int previewIndex);
    void onElementClicked(int elementIndex);
}
