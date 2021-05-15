package cz.whiterabbit.gui.swing.sidePanel.Panels;

import cz.whiterabbit.gui.swing.customComponents.CustomLabel;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;
import cz.whiterabbit.gui.swing.sidePanel.listeners.InfoPanelListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InfoPanel extends GeneralSidePanel {
    private List<InfoPanelListener> panelListeners;

    private CustomLabel playerOnMove;
    private CustomLabel playerOnMoveText;
    private CustomLabel figuresTitle;
    private CustomLabel figuresBlack;
    private CustomLabel figuresBlackValue;
    private CustomLabel figuresWhite;
    private CustomLabel figuresWhiteValue;
    private CustomLabel roundInfoTitle;
    private CustomLabel roundsTotal;
    private CustomLabel roundsTotalValue;
    private CustomLabel roundsWithoutCapture;
    private CustomLabel roundsWithoutCaptureValue;

    private CustomMenuButton newGameButton;
    private CustomMenuButton getMoveFromComputerButton;
    private CustomMenuButton rulesButton;

    public InfoPanel(){
        super();

        panelListeners = new ArrayList<>();

        initializeComponents();
        initializeListeners();
        layoutComponents();
    }

    private void initializeListeners() {
        newGameButton.addActionListener(x -> {
            panelListeners.iterator().forEachRemaining(l -> l.onNewGameClicked());
        });
        getMoveFromComputerButton.addActionListener(x -> {
            panelListeners.iterator().forEachRemaining(l -> l.onSuggestMoveClicked());
        });
        rulesButton.addActionListener(x -> {
            panelListeners.iterator().forEachRemaining(l -> l.onRulesClicked());
        });
    }

    private void initializeComponents() {
        playerOnMove = new CustomLabel("<html><h1>WHITE</h1></html>");
        playerOnMoveText = new CustomLabel("player on move");
        figuresTitle = new CustomLabel("<html><h3>Figures</h3></html>");
        figuresBlack = new CustomLabel("Black player:");
        figuresBlackValue = new CustomLabel("16");
        figuresWhite = new CustomLabel("White player:");
        figuresWhiteValue = new CustomLabel("16");
        roundInfoTitle = new CustomLabel("<html><h3>Rounds</h3></html>");
        roundsTotal = new CustomLabel("Total rounds:");
        roundsTotalValue = new CustomLabel("0");
        roundsWithoutCapture = new CustomLabel("Without capture:");
        roundsWithoutCaptureValue = new CustomLabel("0");

        newGameButton = new CustomMenuButton("New game");
        getMoveFromComputerButton= new CustomMenuButton("Suggest move");
        rulesButton = new CustomMenuButton("Rules");


    }

    private void layoutComponents(){
        //INITIALIZE
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.1;
        gc.weighty = 0.1;

        gc.insets = new Insets(0,10,0,10);

        gc.gridx=0;
        gc.gridy=0;
        //LEVEL 0
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.PAGE_END;
        //gc.insets = new Insets(10,60,0,60);
        add(playerOnMove, gc);


        //Level 1
        gc.gridy++;
        gc.anchor = GridBagConstraints.PAGE_START;
        gc.insets = new Insets(0,10,0,10);
        add(playerOnMoveText, gc);

        //Level 2
        gc.gridwidth = 1;
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;

        add(figuresTitle, gc);

        //Level 3
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;

        add(figuresBlack, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        add(figuresBlackValue, gc);

        //LEVEL 4
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        add(figuresWhite, gc);

        gc.gridx ++;
        gc.anchor = GridBagConstraints.EAST;
        add(figuresWhiteValue, gc);

        //level 8
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(roundInfoTitle, gc);

        //level 9
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        add(roundsTotal, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        add(roundsTotalValue, gc);

        //level 10;
        gc.gridy++;
        gc.gridx =0;
        gc.anchor = GridBagConstraints.WEST;
        add(roundsWithoutCapture, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        add(roundsWithoutCaptureValue, gc);

        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 2;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.SOUTH;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(30,10,0,10);
        add(newGameButton, gc);

        gc.gridy++;
        gc.weighty = 0.1;
        gc.insets = new Insets(0,10,0,10);
        add(rulesButton, gc);

        gc.gridy++;
        gc.weighty = 10;
        gc.anchor = GridBagConstraints.NORTH;
        add(getMoveFromComputerButton, gc);
    }

    public void setPlayerOnMove(boolean playerType){
        if(playerType){
            playerOnMove.setText("<html><h1>WHITE</h1></html>");
        }else{
            playerOnMove.setText("<html><h1>BLACK</h1></html>");
        }
    }

    public void updateInfoTable(boolean playerType, int blackFigures, int whiteFigures, int totalRounds, int noCaptureRounds){
        setPlayerOnMove(playerType);
        figuresBlackValue.setText(String.valueOf(blackFigures));
        figuresWhiteValue.setText(String.valueOf(whiteFigures));
        roundsTotalValue.setText(String.valueOf(totalRounds));
        roundsWithoutCaptureValue.setText(String.valueOf(noCaptureRounds));
    }

    public void addPanelListener(InfoPanelListener panelListener){
        panelListeners.add(panelListener);
    }
}
