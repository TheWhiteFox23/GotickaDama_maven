package cz.whiterabbit.gui.swing.sidePanel;

import cz.whiterabbit.gui.swing.*;
import cz.whiterabbit.gui.swing.ElementList.ListCrate;
import cz.whiterabbit.gui.swing.sidePanel.Panels.*;
import cz.whiterabbit.gui.swing.sidePanel.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SidePanel extends JPanel {
    List<SidePanelListener> sidePanelListeners;

    private Color bgColor = new Color(224,226,244);
    protected Dimension arcs = new Dimension(30, 30);

    private ButtonsPanel buttonsPanel;

    private InfoPanel infoPanel;
    private SettingsPanel settingsPanel;
    private HistoryPanel historyPanel;
    private SavePanel savePanel;
    private LoadPanel loadPanel;

    private PanelType activePanel;

    public SidePanel()  {
        sidePanelListeners = new ArrayList<>();

        buttonsPanel = new ButtonsPanel();

        settingsPanel = new SettingsPanel();
        historyPanel = new HistoryPanel();
        savePanel = new SavePanel();
        loadPanel = new LoadPanel();
        infoPanel = new InfoPanel();
        
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty = 0.1;
        gc.weightx = 0.01;

        gc.gridy=0;
        gc.gridx=0;

        add(buttonsPanel, gc);

        gc.gridx=1;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(10,0,10,10);
        gc.weightx =1;
        add(infoPanel, gc);
        add(historyPanel, gc);
        add(settingsPanel, gc);
        add(savePanel, gc);
        add(loadPanel, gc);

        initializeListeners();

        setBackground(bgColor);
        setOpaque(false);
    }

    private void initializeListeners(){
        buttonsPanel.addButtonPanelListener(new ButtonsPanelListener() {
            @Override
            public void onInfoClicked() {
                manageActivePanelChange(PanelType.infoPanel);
                sidePanelListeners.iterator().forEachRemaining(l -> l.onInfoClicked());
            }

            @Override
            public void onSettingsClicked() {
                manageActivePanelChange(PanelType.settingsPanel);
                sidePanelListeners.iterator().forEachRemaining(l -> l.onSettingsClicked());
            }

            @Override
            public void onHistoryClicked() {
                manageActivePanelChange(PanelType.historyPanel);
                sidePanelListeners.iterator().forEachRemaining(l -> l.onHistoryClicked());
            }

            @Override
            public void onSaveClicked() {
                manageActivePanelChange(PanelType.savePanel);
                sidePanelListeners.iterator().forEachRemaining(l -> l.onSaveClicked());
            }

            @Override
            public void onLoadClicked() {
                manageActivePanelChange(PanelType.loadPanel);
                sidePanelListeners.iterator().forEachRemaining(l -> l.onLoadClicked());
            }
        });
        historyPanel.addPanelListener(new HistoryPanelListener() {
            @Override
            public void onPreviewPressed(int previewIndex) {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onPreviewPressed(previewIndex));
            }

            @Override
            public void onPreviewReleased(int previewIndex) {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onPreviewReleased(previewIndex));
            }

            @Override
            public void onReverseClicked(int reverseIndex) {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onReverseClicked(reverseIndex));
            }
        });
        savePanel.addPanelListener(new SavePanelListener() {
            @Override
            public void onSaveClicked(String filename) {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onSaveFileClicked(filename));
            }
        });
        loadPanel.addPanelListener(new LoadPanelListener() {
            @Override
            public void onLoadFileClicked(String filename) {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onLoadFileClicked(filename));
            }
        });
        settingsPanel.addPanelListener(new SettingsPanelListener() {
            @Override
            public void onSettingsConfirmed() {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onSettingsConfirmed());
            }
        });

        infoPanel.addPanelListener(new InfoPanelListener() {
            @Override
            public void onNewGameClicked() {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onNewGameClicked());
            }

            @Override
            public void onSuggestMoveClicked() {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onSuggestMoveClicked());
            }

            @Override
            public void onRulesClicked() {
                sidePanelListeners.iterator().forEachRemaining(l -> l.onRulesClicked());
            }
        });
    }

    //todo refactor
    private void manageActivePanelChange(PanelType panel){
        if(activePanel == null || activePanel != panel){
            if(activePanel != null){
                getActivePanel().setVisible(false);
                buttonsPanel.setButtonActive(activePanel, false);
            }
            activePanel = panel;
            buttonsPanel.setButtonActive(panel, true);
            getActivePanel().setVisible(true);
        }else{
            if(activePanel != null){
                getActivePanel().setVisible(false);
                buttonsPanel.setButtonActive(activePanel, false);
            }
            activePanel = null;
        }
    }

    private JPanel getActivePanel(){
        if(activePanel != null){
            switch (activePanel){
                case infoPanel:
                    return infoPanel;
                case settingsPanel:
                    return settingsPanel;
                case historyPanel:
                    return  historyPanel;
                case loadPanel:
                    return loadPanel;
                case savePanel:
                    return savePanel;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width,
                height, arcs.width, arcs.height);

        graphics.setStroke(new BasicStroke());
    }

    public void setPlayerOnMove(boolean playerOnMove){
        infoPanel.setPlayerOnMove(playerOnMove);
    }

    public void addPanelListener(SidePanelListener panelListener){
        sidePanelListeners.add(panelListener);
    }

    public void updateInfoTable(boolean playerType, int blackFigures, int whiteFigures, int totalRounds, int noCaptureRounds){
        infoPanel.updateInfoTable(playerType, blackFigures, whiteFigures, totalRounds, noCaptureRounds );
    }

    public void setHistoryList(List<ListCrate> historyList){
        historyPanel.setHistoryList(historyList);
    }

    public int getWhitePlayerType(){
        return settingsPanel.getWhitePlayerType();
    }

    public int getBlackPlayerType() {
        return settingsPanel.getBlackPlayerType();
    }

    public int getWhitePlayerDifficulty() {
        return settingsPanel.getWhitePlayerDifficulty();
    }

    public int getBlackPlayerDifficulty() {
        return settingsPanel.getBlackPlayerDifficulty();
    }

    public void setWhitePlayerType(int whitePlayerType) {
        settingsPanel.setWhitePlayerType(whitePlayerType);
    }

    public void setBlackPlayerType(int blackPlayerType) {
        settingsPanel.setBlackPlayerType(blackPlayerType);
    }

    public void setWhitePlayerDifficulty(int whitePlayerDifficulty) {
        settingsPanel.setWhitePlayerDifficulty(whitePlayerDifficulty);
    }

    public void setBlackPlayerDifficulty(int blackPlayerDifficulty) {
        settingsPanel.setBlackPlayerDifficulty(blackPlayerDifficulty);
    }
}
