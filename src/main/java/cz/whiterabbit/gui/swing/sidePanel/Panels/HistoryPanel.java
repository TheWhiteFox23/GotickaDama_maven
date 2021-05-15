package cz.whiterabbit.gui.swing.sidePanel.Panels;

import cz.whiterabbit.gui.swing.ElementList.HistoryListListener;
import cz.whiterabbit.gui.swing.ElementList.HistoryListPanel;
import cz.whiterabbit.gui.swing.ElementList.ListCrate;
import cz.whiterabbit.gui.swing.customComponents.CustomLabel;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;
import cz.whiterabbit.gui.swing.customComponents.CustomScrollPane;
import cz.whiterabbit.gui.swing.sidePanel.listeners.HistoryPanelListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryPanel extends GeneralSidePanel{
    private List<HistoryPanelListener> panelListeners = new ArrayList<>();

    private CustomLabel title = new CustomLabel("<html><h1>History</h1></html>");
    private CustomMenuButton reverseButton = new CustomMenuButton("Reverse to selected point");
    private HistoryListPanel historyListPanel = new HistoryListPanel();

    public HistoryPanel(){
        super();
        layoutComponents();
        initializeListeners();
    }

    private void initializeListeners() {
        historyListPanel.addHistoryListListener(new HistoryListListener() {
            @Override
            public void onPreviewPressed(int previewIndex) {
                panelListeners.iterator().forEachRemaining(l -> l.onPreviewPressed(previewIndex));
            }

            @Override
            public void onPreviewReleased(int previewIndex) {
                panelListeners.iterator().forEachRemaining(l -> l.onPreviewReleased(previewIndex));
            }

            @Override
            public void onElementClicked(int elementIndex) {
            }
        });

        reverseButton.addActionListener(e -> {
            panelListeners.iterator().forEachRemaining(l -> l.onReverseClicked(historyListPanel.getSelectedIndex()));
        });
    }

    private void layoutComponents() {
        setLayout( new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty = 0.1;
        gc.weightx = 0.1;

        gc.insets = new Insets(10,10,10,10);

        gc.gridx = 0;
        gc.gridy =0;
        add(title, gc);

        gc.weighty = 10;
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        CustomScrollPane scrollPane = new CustomScrollPane(historyListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, gc);

        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        add(reverseButton, gc);
    }

    public void setHistoryList(List<ListCrate> historyList){
        historyListPanel.setHistoryList(historyList);
    }

    public void addPanelListener(HistoryPanelListener listener){
        panelListeners.add(listener);
    }

}
