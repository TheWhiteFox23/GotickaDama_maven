package cz.whiterabbit.gui.swing.ElementList;

import cz.whiterabbit.gui.swing.customComponents.CustomLabel;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class HistoryListElementImpl extends JPanel implements HistoryListElement{
    private List<HistoryListListener> listenerList;

    private int index;
    private byte[] move;

    private CustomLabel moveLabel;
    private CustomLabel indexLabel;

    private Color textColor;
    private Color backgroundColor;

    private CustomMenuButton previewButton;
    private boolean selected;

    public HistoryListElementImpl(byte[] move, int index){
        this.move = move;
        this.index = index;
        listenerList = new ArrayList<>();
        moveLabel = new CustomLabel(moveToString(move));
        indexLabel = new CustomLabel("undo index:" + String.valueOf(index));

        previewButton = new CustomMenuButton("show");
        previewButton.setBorder(new EmptyBorder(3,10,3,10));
        previewButton.setVisible(false);
        previewButton.setVisible(true);
        layoutComponents();
        initializeListeners();
        manageColors();
    }

    private void initializeListeners() {
        previewButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                listenerList.iterator().forEachRemaining(l -> l.onPreviewPressed(index));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                listenerList.iterator().forEachRemaining(l -> l.onPreviewReleased(index));
            }

            @Override
            public void mouseEntered(MouseEvent e) {


            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                listenerList.iterator().forEachRemaining(l -> l.onElementClicked(index));
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private String moveToString(byte[] move) {
        List<Byte> jumps = getJumpPositions(move);
        String moveSignature = "";
        for(int i = 0; i<jumps.size()-1; i++){
            moveSignature += convertPositionsIntoString(jumps.get(i)) + "--";
        }
        moveSignature +=  convertPositionsIntoString(jumps.get(jumps.size()-1));
        return moveSignature;
    }

    private List<Byte>getJumpPositions(byte[] move){
        List<Byte> moves = new ArrayList<>();
        boolean figure = move[1] > 0;
        moves.add(move[0]);
        for(int i = 2; i<move.length; i+=3){
            if((move[i]>0 && figure) || (move[i]<0 && !figure)){
                moves.add(move[i-2]);
            }
        }
        return moves;
    }

    private String convertPositionsIntoString(int number){
        char[] numbers = "12345678".toCharArray();
        char[] characters = "ABCDEFGH".toCharArray();
        int numIndex = number/8;
        int charIndex = number%8;
        return "" + numbers[numIndex] + characters[charIndex];
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx=0;
        gc.gridy=0;
        add(moveLabel, gc);

        gc.gridy++;
        add(indexLabel, gc);

        //gc.gridheight = 2;
        //gc.gridx = 1;
        gc.gridx++;
        add(previewButton, gc);
    }

    public void addHistoryListListener(HistoryListListener listener){
        listenerList.add(listener);
    }

    public void setSelected(boolean selected){
        this.selected = selected;
        if(selected){
            textColor = new Color(224,226,244);
            backgroundColor = new Color(66, 70, 112);
            refreshColors();
        }else{
            manageColors();

        }
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public byte[] getMove() {
        return move;
    }

    public boolean isSelected(){
        return selected;
    }

    private void manageColors(){
        if(move[1]>0){
            backgroundColor = new Color(224,226,244);
            textColor = new Color(66, 70, 112);
        }else{
            backgroundColor = new Color(153, 158, 202);
            textColor = new Color(66, 70, 112);
        }
        refreshColors();
    }

    private void refreshColors(){
        setBackground(backgroundColor);
        moveLabel.setForeground(textColor);
        indexLabel.setForeground(textColor);
    }
}
