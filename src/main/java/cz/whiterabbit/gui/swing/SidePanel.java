package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.listeners.ButtonsPanelListener;
import cz.whiterabbit.gui.swing.listeners.SidePanelListener;

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
    private JPanel settingsPanel;
    private JPanel historyPanel;
    private JPanel savePanel;
    private JPanel loadPanel;

    private PanelType activePanel;

    public SidePanel()  {
        sidePanelListeners = new ArrayList<>();

        buttonsPanel = new ButtonsPanel();

        settingsPanel = new JPanel();
        historyPanel = new JPanel();
        savePanel = new JPanel();
        loadPanel = new JPanel();
        infoPanel = new InfoPanel();
        
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.weighty = 1;
        gb.weightx = 1;

        gb.gridy=0;
        gb.gridx=0;

        add(buttonsPanel, gb);

        gb.gridx=1;
        gb.fill = GridBagConstraints.BOTH;
        gb.insets = new Insets(10,0,10,10);
        add(infoPanel, gb);

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

}
