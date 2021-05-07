package cz.whiterabbit.gui;

import cz.whiterabbit.elements.Board;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.gui.swing.PlayBoard;
import cz.whiterabbit.gui.swing.listeners.PlayBoardListener;
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
    //MOVE CALCULATION
    private Board board;
    private byte[] moveToConfirm;
    private byte[] redoMove;


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
        board = new Board();

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
                        byte[] finishingMove = getFinishingMove(field);
                        if(finishingMove != null){
                            finishMove(finishingMove);
                        }
                        //release move initialize
                        moveStart = -1;
                        playBoard.setHighlightFields(new int[0]);
                        playBoard.setLandingHighlight(new int[0]);
                        frame.repaint();
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
                gameController.applyMove(moveToConfirm);
                playBoard.setBoard(gameController.getBoardArr());
                confirmButton.setEnabled(false);
                moveMade = false;
                gameController.switchPlayerType();
                moveToConfirm = null;
                redoMove = null;
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
        if(moveToConfirm != null){
            playBoard.setBoard(gameController.getBoardArr());
            redoMove = moveToConfirm;
            moveToConfirm = null;
            moveMade = false;
        }else if(gameController.undo()){
            playBoard.setBoard(gameController.getBoardArr());
            moveMade = false;
            gameController.switchPlayerType();
            sidePanel.setPlayerOnMove(gameController.isPlayerType());
        }
        frame.repaint();
    }

    private void redo(){
        if(redoMove != null){
            playBoard.setBoard(board.applyMove(playBoard.getBoard(), redoMove));
            moveToConfirm = redoMove;
            redoMove = null;
            moveMade = false;
        }else if(gameController.redo()){
            playBoard.setBoard(gameController.getBoardArr());
            moveMade = false;
            gameController.switchPlayerType();
            sidePanel.setPlayerOnMove(gameController.isPlayerType());
        }
        frame.repaint();
    }

    private void finishMove(byte[] by){
        moveToConfirm = by;
        playBoard.setBoard(board.applyMove(playBoard.getBoard(), moveToConfirm));
        moveMade = true;
        confirmButton.setEnabled(true);
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
