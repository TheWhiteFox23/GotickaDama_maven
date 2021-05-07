package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.listeners.ButtonsPanelListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ButtonsPanel extends JPanel {
    //LISTENERS
    private List<ButtonsPanelListener> buttonsPanelListener = new ArrayList<>();

    private GeneralSidePanelButton gameInfoButton;
    private GeneralSidePanelButton historyButton;
    private GeneralSidePanelButton settingsButton;
    private GeneralSidePanelButton saveButton;
    private GeneralSidePanelButton loadButton;

    public ButtonsPanel(){
        initializeButton();
        initializeListeners();
        layoutComponents();
        setOpaque(false);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        add(gameInfoButton, gc);

        gc.gridy = 1;
        add(historyButton, gc);

        gc.gridy =2;
        add(settingsButton, gc);

        gc.gridy =3;
        add(saveButton, gc);

        gc.gridy = 4;
        add(loadButton, gc);
    }

    private void initializeListeners() {
        gameInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonsPanelListener.iterator().forEachRemaining(l -> l.onInfoClicked());
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonsPanelListener.iterator().forEachRemaining(l -> l.onSettingsClicked());
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonsPanelListener.iterator().forEachRemaining(l -> l.onHistoryClicked());
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonsPanelListener.iterator().forEachRemaining(l -> l.onSaveClicked());
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonsPanelListener.iterator().forEachRemaining(l -> l.onLoadClicked());
            }
        });
    }

    private void initializeButton(){
        String basePath = "src\\main\\resources\\Icons\\";
        gameInfoButton = tryInitializeButton(basePath + "InfoIcon.png", basePath + "InfoIconSelect.png", "info");
        settingsButton = tryInitializeButton(basePath + "SettingsIcon.png", basePath + "SettingsIconSelect.png", "settings");
        historyButton = tryInitializeButton( basePath + "HistoryIcon.png", basePath + "HistoryIconSelect.png", "history");
        saveButton = tryInitializeButton( basePath + "SaveIcon.png", basePath + "SaveIconSelect.png", "save");
        loadButton = tryInitializeButton( basePath + "LoadIcon.png", basePath + "LoadIconSelect.png", "load");
    }

    private GeneralSidePanelButton tryInitializeButton(String basic, String selected, String failText){
        try{
            InputStream is = new BufferedInputStream(new FileInputStream(basic));
            Image basicImage = ImageIO.read(is);
            Icon basicIcon = new ImageIcon(basicImage);
            is = new BufferedInputStream(new FileInputStream(selected));
            Image selectedImage = ImageIO.read(is);
            Icon selectedIcon = new ImageIcon(selectedImage);
            return new GeneralSidePanelButton(basicIcon, selectedIcon);
        } catch (Exception e){
            return new GeneralSidePanelButton(failText);
        }
    }

    public void addButtonPanelListener(ButtonsPanelListener listener){
        buttonsPanelListener.add(listener);
    }

    public void setButtonActive(PanelType panelType, boolean b){
        switch (panelType){
            case savePanel:
                saveButton.setActive(b);
                break;
            case infoPanel:
                gameInfoButton.setActive(b);
                break;
            case loadPanel:
                loadButton.setActive(b);
                break;
            case historyPanel:
                historyButton.setActive(b);
                break;
            case settingsPanel:
                settingsButton.setActive(b);
                break;
        }

    }
}
