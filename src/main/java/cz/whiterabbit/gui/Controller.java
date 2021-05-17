package cz.whiterabbit.gui;

import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.InvalidMoveException;
import cz.whiterabbit.gui.swing.PlayBoard;
import cz.whiterabbit.gui.swing.PlayBoardListener;
import cz.whiterabbit.gui.swing.SidePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Link between gameController and GUI. Create frame of the game and manage all of the comunication
 */
public class Controller {
    //CONTROLS
    private JButton confirmButton;
    private JButton undoButton;
    private JButton redoButton;

    private JFrame frame;

    private SidePanel sidePanel;
    //GAME CONTROL
    private boolean moveMade;


    //Game menu
    private PlayBoard playBoard;
    private GameController gameController;

    private int moveStart = -1;

    public Controller() throws FileNotFoundException {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500,500);

        sidePanel = new SidePanel();
        frame.add(sidePanel, BorderLayout.WEST);

        confirmButton = new JButton("confirm");
        undoButton =  new JButton("undo");
        redoButton = new JButton("redo");
        confirmButton.setEnabled(false);

        playBoard = new PlayBoard();
        gameController = new GameController();
        gameController.startGame();

        initializeListeners(frame);
        frame.add(playBoard, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.WEST);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(undoButton, BorderLayout.SOUTH);
        controlPanel.add(confirmButton, BorderLayout.SOUTH);
        controlPanel.add(redoButton, BorderLayout.SOUTH);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    //TODO move to separate class ???
    private void initializeListeners(JFrame frame) {
        playBoard.addPlayBoardListener(new PlayBoardListener() {
            @Override
            public void onFieldClicked(int field) {
                if (!moveMade) {
                    if (moveStart == -1) {
                        initializeMove(field, frame);
                    } else {
                        finishMoveAndCancelSelection(field, frame);
                    }
                }
            }

            @Override
            public void onFieldEntered(int field) {
                if(!moveMade){
                    playBoard.setHighlightFields(convertMoveToHighlightFields(getMovesFromThePosition(field)));
                    frame.repaint();
                }
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmButton.setEnabled(false);
                moveMade = false;
            }
        });
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

    }

    private void undo() {
        if(gameController.undo()){
            playBoard.setBoard(gameController.getBoardArr());
            frame.repaint();
            moveMade = false;
            gameController.switchPlayerType();
            sidePanel.setPlayerOnMove(gameController.isPlayerType());
        }
    }

    private void redo(){
        if(gameController.redo()){
            playBoard.setBoard(gameController.getBoardArr());
            frame.repaint();
            moveMade = false;
            gameController.switchPlayerType();
            sidePanel.setPlayerOnMove(gameController.isPlayerType());
        }
    }

    private void finishMoveAndCancelSelection(int field, JFrame frame) {
        byte[] finishingMove = getFinishingMove(field);
        if(finishingMove != null)finishMove(finishingMove);
        playBoard.setLandingHighlight(new int[0]);
        playBoard.setStartHighlight(-1);
        moveStart = -1;
        frame.repaint();
        gameController.switchPlayerType();
        sidePanel.setPlayerOnMove(gameController.isPlayerType());
    }

    private void finishMove(byte[] by) {
        try {
            gameController.applyMove(by);
            playBoard.setBoard(gameController.getBoardArr());
            moveMade = true;
            confirmButton.setEnabled(true);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }

    private void initializeMove(int field, JFrame frame) {
        if(getMovesFromThePosition(field).size() != 0){
            moveStart = field;
            playBoard.setLandingHighlight(moveToLandingFields(getMovesFromThePosition(field)));
            playBoard.setStartHighlight(moveStart);
            frame.repaint();
        }
    }

    public int[] convertMoveToHighlightFields(List<byte[]> highlightMove) {
        List<Integer> highlightFields = new ArrayList<>();
        for(byte[] by : highlightMove){
            playBoard.setStartHighlight(by[0]);
            int peace = by[1];
            for(int i = 2; i<by.length; i+=3){
                if(by[i] == peace){
                    highlightFields.add((int) by[i-2]);
                }
            }
        }
        int[] array = new int[highlightFields.size()];
        int index = 0;
        for(int i: highlightFields){
            array[index] = i;
            index++;
        }
        if(array.length == 0) playBoard.setStartHighlight(-1);
        return array;
    }

    public int[] moveToLandingFields(List<byte[]> highlightMove) {
        List<Integer> highlightFields = new ArrayList<>();
        for(byte[] by : highlightMove){
            highlightFields.add((int)by[by.length-3]);
        }
        int[] array = new int[highlightFields.size()];
        int index = 0;
        for(int i: highlightFields){
            array[index] = i;
            index++;
        }
        return array;
    }

    public List<byte[]> getMovesFromThePosition(int position){
        List<byte[]> movesFromPosition = new ArrayList<>();
        List<byte[]> validMoves = gameController.getAllValidMoves();
        for(byte[] by: validMoves){
            if (by[0] == position) movesFromPosition.add(by);
        }
        return movesFromPosition;
    }

    private byte[] getFinishingMove(int field){
        List<byte[]> movesFromInitialPosition = getMovesFromThePosition(moveStart);
        for(byte[] by : movesFromInitialPosition){
            if(by[by.length-3] == field) return by;
        }
        return null;
    }

}
