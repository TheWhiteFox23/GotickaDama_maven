package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.listeners.BottomPanelListener;
import cz.whiterabbit.gui.swing.listeners.FrameListener;
import cz.whiterabbit.gui.swing.listeners.PlayBoardListener;
import cz.whiterabbit.gui.swing.listeners.SidePanelListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    List<FrameListener> frameListeners;

    private SidePanel sidePanel;
    private PlayBoard playBoard;
    private BottomPanel bottomPanel;

    public GameFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500,500);

        frameListeners = new ArrayList<>();

        sidePanel = new SidePanel();
        playBoard = new PlayBoard();
        bottomPanel = new BottomPanel();
        layoutComponents();

        initializeListeners();
    }

    private void initializeListeners() {
        sidePanel.addPanelListener(new SidePanelListener() {
            @Override
            public void onHistoryClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onHistoryClicked());
            }

            @Override
            public void onInfoClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onInfoClicked());
            }

            @Override
            public void onSettingsClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onSettingsClicked());
            }

            @Override
            public void onSaveClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onSaveClicked());
            }

            @Override
            public void onLoadClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onLoadClicked());
            }
        });
        bottomPanel.addPanelListener(new BottomPanelListener() {
            @Override
            public void onConfirmClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onConfirmClicked());
            }

            @Override
            public void onUndoClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onUndoClicked());
            }

            @Override
            public void onRedoClicked() {
                frameListeners.iterator().forEachRemaining(l->l.onRedoClicked());
            }
        });
        playBoard.addPlayBoardListener(new PlayBoardListener() {
            @Override
            public void onFieldClicked(int field) {
                frameListeners.iterator().forEachRemaining(l -> l.onFieldClicked(field));
            }

            @Override
            public void onFieldEntered(int field) {
                frameListeners.iterator().forEachRemaining(l -> l.onFieldSelected(field));
            }
        });
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(sidePanel, BorderLayout.WEST);
        add(playBoard, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public SidePanel getSidePanel() {
        return sidePanel;
    }

    public void setSidePanel(SidePanel sidePanel) {
        this.sidePanel = sidePanel;
    }

    public PlayBoard getPlayBoard() {
        return playBoard;
    }

    public void setPlayBoard(PlayBoard playBoard) {
        this.playBoard = playBoard;
    }

    public BottomPanel getBottomPanel() {
        return bottomPanel;
    }

    public void setBottomPanel(BottomPanel bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    public void addFrameListener(FrameListener listener){
        frameListeners.add(listener);
    }

    public void setContinueButtonEnabled(boolean enabled){
        bottomPanel.setConfirmButtonEnabled(enabled);
    }

    public void setConfirmButtonText(String text){
        bottomPanel.setConfirmButtonTest(text);
    }
}
