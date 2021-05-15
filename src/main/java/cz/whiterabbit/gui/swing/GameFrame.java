package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.ElementList.ListCrate;
import cz.whiterabbit.gui.swing.listeners.BottomPanelListener;
import cz.whiterabbit.gui.swing.listeners.FrameListener;
import cz.whiterabbit.gui.swing.listeners.PlayBoardListener;
import cz.whiterabbit.gui.swing.sidePanel.listeners.SidePanelListener;
import cz.whiterabbit.gui.swing.sidePanel.SidePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private List<FrameListener> frameListeners;

    private Color bgColor = new Color(83, 92, 178);

    private SidePanel sidePanel;
    private PlayBoard playBoard;
    private BottomPanel bottomPanel;
    private RoundCornerPanel playBoardPanel;

    public GameFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800,500);
        setMinimumSize(new Dimension(700,500));
        getContentPane().setBackground(bgColor);

        //setMinimumSize(new Dimension(540, 350));

        frameListeners = new ArrayList<>();

        sidePanel = new SidePanel();
        playBoard = new PlayBoard();
        bottomPanel = new BottomPanel();
        playBoardPanel = new RoundCornerPanel(playBoard);
        layoutComponents();

        initializeListeners();
    }

    private void initializeListeners() {
        sidePanel.addPanelListener(new SidePanelListener() {
            @Override
            public void onHistoryClicked() {
            }

            @Override
            public void onInfoClicked() {

            }

            @Override
            public void onSettingsClicked() {

            }

            @Override
            public void onSaveClicked() {

            }

            @Override
            public void onLoadClicked() {

            }

            @Override
            public void onPreviewPressed(int previewIndex) {
                frameListeners.iterator().forEachRemaining(l -> l.onPreviewPressed(previewIndex));
            }

            @Override
            public void onPreviewReleased(int previewIndex) {
                frameListeners.iterator().forEachRemaining(l -> l.onPreviewReleased(previewIndex));
            }

            @Override
            public void onReverseClicked(int reverseIndex) {
                frameListeners.iterator().forEachRemaining(l -> l.onReverseClicked(reverseIndex));
            }

            @Override
            public void onSaveFileClicked(String fileName) {
                frameListeners.iterator().forEachRemaining(l -> l.onSaveFileClicked(fileName));
            }

            @Override
            public void onLoadFileClicked(String filename) {
                frameListeners.iterator().forEachRemaining(l -> l.onLoadFileClicked(filename));
            }

            @Override
            public void onSettingsConfirmed() {
                frameListeners.iterator().forEachRemaining(l -> l.onSettingsConfirm());
            }

            @Override
            public void onNewGameClicked() {
                frameListeners.iterator().forEachRemaining(l -> l.onNewGameClicked());
            }

            @Override
            public void onSuggestMoveClicked() {
                frameListeners.iterator().forEachRemaining(l-> l.onSuggestMoveClicked());
            }

            @Override
            public void onRulesClicked() {
                frameListeners.iterator().forEachRemaining(l -> l.onRulesClicked());
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
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weighty = 1;
        gc.weightx = 0.01;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridheight = 1;
        gc.insets =  new Insets(10,10,10,10);
        gc.fill = GridBagConstraints.VERTICAL;
        add(sidePanel, gc);
        //add(new JLabel("TEST"), gc);

        JPanel holderPanel = new JPanel();
        holderPanel.setLayout(new GridBagLayout());
        holderPanel.add(new RoundCornerPanel(playBoard));
        holderPanel.setOpaque(false);

        gc.gridheight = 1;
        gc.gridx = 1;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.CENTER;
        //add(new RoundCornerPanel(playBoard), gc);
        add(holderPanel, gc);

        gc.gridy++;
        gc.weighty = 0.01;
        gc.fill = GridBagConstraints.NONE;
        add(bottomPanel, gc);
        //add(new JLabel("test"), gc);



        /*setLayout(new BorderLayout());
        add(sidePanel, BorderLayout.WEST);
        add(holderPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);*/
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

    public void updateInfoTable(boolean playerType, int blackFigures, int whiteFigures, int totalRounds, int noCaptureRounds){
        sidePanel.updateInfoTable(playerType, blackFigures, whiteFigures, totalRounds, noCaptureRounds );
    }

    public void setHistoryList(List<ListCrate> historyList){
        sidePanel.setHistoryList(historyList);
    }

    public int getWhitePlayerType(){
        return sidePanel.getWhitePlayerType();
    }

    public int getBlackPlayerType() {
        return sidePanel.getBlackPlayerType();
    }

    public int getWhitePlayerDifficulty() {
        return sidePanel.getWhitePlayerDifficulty();
    }

    public int getBlackPlayerDifficulty() {
        return sidePanel.getBlackPlayerDifficulty();
    }

    public void setWhitePlayerType(int whitePlayerType) {
        sidePanel.setWhitePlayerType(whitePlayerType);
    }

    public void setBlackPlayerType(int blackPlayerType) {
        sidePanel.setBlackPlayerType(blackPlayerType);
    }

    public void setWhitePlayerDifficulty(int whitePlayerDifficulty) {
        sidePanel.setWhitePlayerDifficulty(whitePlayerDifficulty);
    }

    public void setBlackPlayerDifficulty(int blackPlayerDifficulty) {
        sidePanel.setBlackPlayerDifficulty(blackPlayerDifficulty);
    }
}
