package cz.whiterabbit.gui.swing;

import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.gui.swing.listeners.GameBordMoveManagerListener;
import cz.whiterabbit.gui.swing.listeners.PlayBoardListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Manage field highlights and detect move selections
 */
public class GameBoardMoveManager {
    //MOVE DETECTION
    private int moveStart = -1;
    private List<byte[]> movesFromStartingPosition;

    private List<GameBordMoveManagerListener> managerListeners;

    private PlayBoard playBoard;
    private GameController gameController;

    private boolean selectionEnabled;

    public GameBoardMoveManager(PlayBoard playBoard, GameController gameController){
        this.managerListeners = new ArrayList<>();
        this.playBoard = playBoard;
        this.gameController = gameController;

        selectionEnabled = false;

        movesFromStartingPosition = new ArrayList<>();

        initializeListeners();
    }

    private void initializeListeners() {
        playBoard.addPlayBoardListener(new PlayBoardListener() {
            @Override
            public void onFieldClicked(int field) {
                if(selectionEnabled) manageFieldClicked(field);
            }

            @Override
            public void onFieldEntered(int field) {
                if(selectionEnabled && moveStart == -1) manageFieldEntered(field);
            }
        });
    }

    private void manageFieldClicked(int field) {
        if(moveStart == -1){
            movesFromStartingPosition = gameController.getAllValidMovesFromPosition(field);
           if(movesFromStartingPosition.size() != 0){
               highlightLandings(movesFromStartingPosition);
               moveStart = field;
           }
        }else{
            movesFromStartingPosition.iterator().forEachRemaining(m -> {
                if(m[m.length-3]== field){
                    managerListeners.iterator().forEachRemaining(l -> l.onMove(m));
                }
            });
            playBoard.clearHighlights();
            moveStart = -1;
        }

    }

    private void manageFieldEntered(int field) {
        playBoard.clearHighlights();
        List<byte[]> movesFromPosition = gameController.getAllValidMovesFromPosition(field);
        movesFromPosition.iterator().forEachRemaining(m -> highlightMove(m));
        managerListeners.iterator().forEachRemaining(l -> l.onRepaintCall());
    }

    private void highlightMove(byte[] move){
        byte player = move[1];
        for(int i = 2; i< move.length; i+=3){
            if(move[i]== player){
                playBoard.addHighlight(move[i-2], PlayBoard.HIGHLIGHT_STEP);
            }
        }
        playBoard.addHighlight(move[0], PlayBoard.HIGHLIGHT_START);
    }

    private void highlightLandings(List<byte[]> landingMoves){
        playBoard.clearHighlights();
        landingMoves.iterator().forEachRemaining(m -> highlightLanding(m));
        managerListeners.iterator().forEachRemaining(l -> l.onRepaintCall());
    }

    private void highlightLanding(byte[] landingMove){
        playBoard.addHighlight(landingMove[0], PlayBoard.HIGHLIGHT_START);
        playBoard.addHighlight(landingMove[landingMove.length-3], PlayBoard.HIGHLIGHT_LANDING);
    }


    public void addListener(GameBordMoveManagerListener listener){
        managerListeners.add(listener);
    }

    public void updateBoard(){
        playBoard.setBoard(gameController.getBoardArr());
        managerListeners.iterator().forEachRemaining(l-> l.onRepaintCall());
    }

    public void previewMove(byte[] move) {
        byte[] previewBoard = gameController.applyMoveToTheBoard(gameController.getBoardArr(), move);
        playBoard.setBoard(previewBoard);
        managerListeners.iterator().forEachRemaining(l-> l.onRepaintCall());
    }

    public void setSelectionEnabled(boolean enabled){
        selectionEnabled = enabled;
    }

    public void setBoard(byte[] boar){
        playBoard.setBoard(boar);
        managerListeners.iterator().forEachRemaining(l-> l.onRepaintCall());
    }
}
