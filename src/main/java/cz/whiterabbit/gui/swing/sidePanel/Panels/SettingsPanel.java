package cz.whiterabbit.gui.swing.sidePanel.Panels;

import cz.whiterabbit.gui.swing.customComponents.CustomComboBox;
import cz.whiterabbit.gui.swing.customComponents.CustomLabel;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;
import cz.whiterabbit.gui.swing.sidePanel.listeners.SettingsPanelListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsPanel extends GeneralSidePanel {
    private List<SettingsPanelListener> panelListeners;

    public final static int PLAYER_COMPUTER = 1;
    public final static int PLAYER_HUMAN = 0;

    public final static int DIFFICULTY_EASY = 0;
    public final static int DIFFICULTY_MEDIUM = 1;
    public final static int DIFFICULTY_HARD = 2;

    private int whitePlayerType =0;
    private int blackPlayerType =0;

    private int whitePlayerDifficulty = 0;
    private int blackPlayerDifficulty = 0;

    private CustomLabel title;
    private CustomLabel whitePlayerLabel;
    private CustomLabel whitePlayerTypeLabel;
    private CustomComboBox whitePlayerCombo;
    private CustomComboBox whitePlayerDifficultyCombo;
    private CustomLabel whitePlayerDifficultyLabel;
    private CustomLabel blackPlayerLabel;
    private CustomLabel blackPlayerTypeLabel;
    private CustomLabel blackPlayerDifficultyLabel;
    private CustomComboBox blackPlayerCombo;
    private CustomComboBox blackPlayerDifficultyCombo;
    private CustomMenuButton confirm;


    public SettingsPanel(){
        super();

        panelListeners = new ArrayList<>();

        initializeComponents();
        layoutComponents();
        initializeListeners();
        actualizeData();
    }

    private void initializeListeners() {
        confirm.addActionListener(x -> {
            actualizeData();

            panelListeners.iterator().forEachRemaining(l -> l.onSettingsConfirmed());

            confirm.setEnabled(false);
        });
        whitePlayerCombo.addActionListener(x -> confirm.setEnabled(true));
        blackPlayerCombo.addActionListener(x -> confirm.setEnabled(true));
        whitePlayerDifficultyCombo.addActionListener(x -> confirm.setEnabled(true));
        blackPlayerDifficultyCombo.addActionListener(x -> confirm.setEnabled(true));
    }

    private void initializeComponents() {
        title = new CustomLabel("<html><h1>Settings</h1></html>");
        whitePlayerLabel = new CustomLabel("<html><h3>White</h3></html>");
        whitePlayerTypeLabel = new CustomLabel("Type");
        whitePlayerCombo = new CustomComboBox();
        DefaultComboBoxModel modelPlayerTypeWhite = new DefaultComboBoxModel();
        modelPlayerTypeWhite.addElement("Human");
        modelPlayerTypeWhite.addElement("Computer");
        whitePlayerCombo.setModel(modelPlayerTypeWhite);
        whitePlayerDifficultyCombo = new CustomComboBox();
        DefaultComboBoxModel modelDifficultyWhite = new DefaultComboBoxModel();
        modelDifficultyWhite.addElement("EASY");
        modelDifficultyWhite.addElement("MEDIUM");
        modelDifficultyWhite.addElement("HARD");
        whitePlayerDifficultyCombo.setModel(modelDifficultyWhite);
        whitePlayerDifficultyLabel = new CustomLabel("Difficulty");
        blackPlayerLabel = new CustomLabel("<html><h3>Black</h3></html>");
        DefaultComboBoxModel modelPlayerTypeBlack = new DefaultComboBoxModel();
        modelPlayerTypeBlack.addElement("Human");
        modelPlayerTypeBlack.addElement("Computer");
        blackPlayerCombo = new CustomComboBox(modelPlayerTypeBlack);
        blackPlayerTypeLabel = new CustomLabel("Type");
        blackPlayerDifficultyLabel = new CustomLabel("Difficulty");
        DefaultComboBoxModel modelDifficultyBlack = new DefaultComboBoxModel();
        modelDifficultyBlack.addElement("EASY");
        modelDifficultyBlack.addElement("MEDIUM");
        modelDifficultyBlack.addElement("HARD");
        blackPlayerDifficultyCombo = new CustomComboBox(modelDifficultyBlack);
        confirm = new CustomMenuButton("Confirm");
        confirm.setEnabled(false);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor= GridBagConstraints.CENTER;
        gc.insets = new Insets(0,10,0,10);
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 2;
        add(title, gc);

        gc.gridy++;
        add(whitePlayerLabel, gc);

        gc.gridy++;
        gc.gridwidth = 1;
        add(whitePlayerTypeLabel, gc);

        gc.gridx = 1;
        add(whitePlayerCombo, gc);

        gc.gridy++;
        gc.gridx = 0;
        add(whitePlayerDifficultyLabel, gc);

        gc.gridx = 1;
        add(whitePlayerDifficultyCombo, gc);

        gc.gridy++;
        gc.gridx= 0;
        gc.gridwidth = 2;
        add(blackPlayerLabel, gc);

        gc.gridy++;
        gc.gridwidth = 1;
        add(blackPlayerTypeLabel, gc);

        gc.gridx = 1;
        add(blackPlayerCombo, gc);

        gc.gridy++;
        gc.gridx = 0;
        add(blackPlayerDifficultyLabel, gc);

        gc.gridx = 1;
        add(blackPlayerDifficultyCombo, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        add(confirm, gc);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);

        gc.gridy++;
        gc.weighty = 1;
        add(spacer, gc);
    }

    private void actualizeData(){
        whitePlayerType = whitePlayerCombo.getSelectedIndex();
        blackPlayerType = blackPlayerCombo.getSelectedIndex();
        whitePlayerDifficulty = whitePlayerDifficultyCombo.getSelectedIndex();
        blackPlayerDifficulty = blackPlayerDifficultyCombo.getSelectedIndex();
    }

    public int getWhitePlayerType(){
        return whitePlayerType;
    }

    public int getBlackPlayerType() {
        return blackPlayerType;
    }

    public int getWhitePlayerDifficulty() {
        return whitePlayerDifficulty;
    }

    public int getBlackPlayerDifficulty() {
        return blackPlayerDifficulty;
    }

    public void setWhitePlayerType(int whitePlayerType) {
        this.whitePlayerType = whitePlayerType;
    }

    public void setBlackPlayerType(int blackPlayerType) {
        this.blackPlayerType = blackPlayerType;
    }

    public void setWhitePlayerDifficulty(int whitePlayerDifficulty) {
        this.whitePlayerDifficulty = whitePlayerDifficulty;
    }

    public void setBlackPlayerDifficulty(int blackPlayerDifficulty) {
        this.blackPlayerDifficulty = blackPlayerDifficulty;
    }

    public void addPanelListener(SettingsPanelListener panelListener){
        panelListeners.add(panelListener);
    }
}
