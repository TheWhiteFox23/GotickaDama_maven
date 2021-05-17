package cz.whiterabbit.gui.swing;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends GeneralSidePanel {
    private JLabel playerOnMove;
    private JLabel playerOnMoveText;
    private JLabel figuresTitle;
    private JLabel figuresBlack;
    private JLabel figuresBlackValue;
    private JLabel figuresWhite;
    private JLabel figuresWhiteValue;
    private JLabel playTimeTitle;
    private JLabel playTimeBlack;
    private JLabel playTimeBlackValue;
    private JLabel playTimeWhite;
    private JLabel playTimeWhiteValue;
    private JLabel roundInfoTitle;
    private JLabel roundsTotal;
    private JLabel roundsTotalValue;
    private JLabel roundsWithoutCapture;
    private JLabel roundsWithoutCaptureValue;

    public InfoPanel(){
        super();
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        playerOnMove = new JLabel("BLACK");
        playerOnMoveText = new JLabel("player on move");
        figuresTitle = new JLabel("Figures");
        figuresBlack = new JLabel("Black player:");
        figuresBlackValue = new JLabel("16");
        figuresWhite = new JLabel("White player:");
        figuresWhiteValue = new JLabel("16");
        playTimeTitle = new JLabel("Play Time");
        playTimeBlack = new JLabel("Black player:");
        playTimeBlackValue = new JLabel("12:40");
        playTimeWhite = new JLabel("White player:");
        playTimeWhiteValue = new JLabel("11:20");
        roundInfoTitle = new JLabel("Round Information");
        roundsTotal = new JLabel("Total rounds:");
        roundsTotalValue = new JLabel("12");
        roundsWithoutCapture = new JLabel("Without capture:");
        roundsWithoutCaptureValue = new JLabel("3");
    }

    private void layoutComponents(){
        //INITIALIZE
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.1f;
        gc.weighty = 0.1f;

        gc.gridx=0;
        gc.gridy=0;
        //LEVEL 0
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.PAGE_END;
        gc.insets = new Insets(10,60,0,60);
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
        gc.anchor = GridBagConstraints.PAGE_START;

        add(figuresBlack, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(figuresBlackValue, gc);

        //LEVEL 4
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(figuresWhite, gc);

        gc.gridx ++;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(figuresWhiteValue, gc);

        //LEVEL 5

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(playTimeTitle, gc);

        //level 6
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(playTimeBlack, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(playTimeBlackValue, gc);

        //Level 7;
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(playTimeWhite, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(playTimeWhiteValue, gc);

        //level 8
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(roundInfoTitle, gc);

        //level 9
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(roundsTotal, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(roundsTotalValue, gc);

        //level 10;
        gc.gridy++;
        gc.gridx =0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(roundsWithoutCapture, gc);

        gc.gridx++;
        gc.weighty = 10;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(roundsWithoutCaptureValue, gc);
    }

    public void setPlayerOnMove(boolean playerType){
        if(playerType){
            playerOnMove.setText("WHITE");
        }else{
            playerOnMove.setText("BLACK");
        }
    }
}
