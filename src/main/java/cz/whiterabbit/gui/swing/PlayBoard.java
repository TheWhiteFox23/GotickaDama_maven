package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.listeners.PlayBoardListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO make board peaces be always square
public class PlayBoard extends JComponent {
    //LISTENERS
    private List<PlayBoardListener> playBoardListeners;

    private int activeField = -1;

    //COLORS
    private Color boardDarkColor = new Color(134,139,198);
    private Color boardLightColor = new Color(244,245,255);
    private Color figureSecondaryLight = new Color(185,188,218);
    private Color figurePrimaryLight = new Color(224,226,244);
    private Color figurePrimaryDark = new Color(66,70,112);
    private Color figureSecondaryDark = new Color(52,56,92);
    public static Color HIGHLIGHT_STEP = new Color(154,198,134);
    public static Color HIGHLIGHT_LANDING =  new Color(198,135,135);
    public static Color HIGHLIGHT_START =  new Color(198,194,135);

    private byte[] board = new byte[]{
             2, 2, 2, 2, 2, 2, 2, 2,
             1, 1, 1, 1, 1, 1, 1, 1,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
            -1,-1,-1,-1,-1,-1,-1,-1,
            -2,-2,-2,-2,-2,-2,-2,-2
    }; //todo show field during the paint

    private float squareWidth;
    private float squareHeight;

    private int[] highlightFields;
    private int[] landingHighlight;
    private int startHighlight = -1;

    private Map<Integer, Color> highlights;

    public PlayBoard(){
        playBoardListeners = new ArrayList<>();
        highlights =  new HashMap<>();
        initializeListeners();
    }

    private void initializeListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("field ID: " +  getFieldId(e.getPoint()));

            }

            @Override
            public void mousePressed(MouseEvent e) {
                fireOnFieldSelectedEvent(e);
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

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int actualActiveField = getFieldId(e.getPoint());
                if(actualActiveField != activeField){
                    int finalActualActiveField = actualActiveField;
                    playBoardListeners.iterator().forEachRemaining(l -> l.onFieldEntered(finalActualActiveField));
                    activeField = actualActiveField;
                }
            }
        });
    }

    private String getCoordinates(Point point) {
        char[] characters = "ABCDEFGH".toCharArray();
        char[] numbers = "12345678".toCharArray();
        int indexCharacter = (int) (point.x/squareWidth);
        int indexNumber = (int) (point.y/squareHeight);
        if(indexCharacter >= 0 && indexCharacter < 8 && indexNumber >= 0 && indexNumber <8){
            return "" + characters[indexCharacter] + numbers[indexNumber];
        }else{
            return null;
        }
    }

    private int getFieldId(Point point){
        int indexColumn = (int) (point.x/squareWidth);
        int indexRow = (int) (point.y/squareHeight);
        if(indexColumn >= 0 && indexColumn < 8 && indexRow >= 0 && indexRow <8){
           return indexRow*8 + indexColumn;
        }else{
            return -1;
        }
    }

    @Override
    public void paint(Graphics g) {
        //so the board is always square
        if(getHeight() > getWidth()){
            setSize(getWidth(), getWidth());
        }else{
            setSize(getHeight(), getHeight());
        }
        Graphics2D g2d = (Graphics2D) g;
        squareWidth = getWidth()/8.0f;
        squareHeight = getHeight()/8.0f;
        for(int i = 0; i< 8; i++){
            for(int j = 0; j< 8; j++){
                Shape square = new Rectangle2D.Float(j*squareWidth, i*squareHeight, squareWidth, squareHeight);
                if(highlights.containsKey(j+(i*8))){
                    g2d.setColor(highlights.get(j+(i*8)));
                }else if((i+j)%2 == 0){
                    g2d.setColor(boardLightColor);
                }else {
                    g2d.setColor(boardDarkColor);
                }
                g2d.fill(square);
                if(board[j+i*8] != 0) drawFigure(g, board[j+i*8],j*squareWidth, i*squareHeight, squareWidth, squareHeight );
            }
        }

        super.paint(g);
    }

    private void drawFigure(Graphics g, int type, float x, float y, float width, float height){
        Color mainColor =  figurePrimaryLight;
        Color secondaryColor = figureSecondaryLight;
        Color centerColor = figurePrimaryLight;
        switch (type){
            case 1:{
                mainColor =  figurePrimaryLight;
                secondaryColor = figureSecondaryLight;
                centerColor = figurePrimaryLight;
                break;
            } case 2:{
                mainColor =  figurePrimaryLight;
                secondaryColor = figureSecondaryLight;
                centerColor = figurePrimaryDark;
                break;
            } case -1:{
                mainColor =  figurePrimaryDark;
                secondaryColor = figureSecondaryDark;
                centerColor = figurePrimaryDark;
                break;
            } case -2:{
                mainColor =  figurePrimaryDark;
                secondaryColor = figureSecondaryDark;
                centerColor = figurePrimaryLight;
                break;
            }
        }
        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Shape circle = new Ellipse2D.Float(x + 0.077f*width, y +0.077f *height, width*0.846f, height*0.846f);
        g2d.setColor(mainColor);
        g2d.fill(circle);
        g2d.setColor(secondaryColor);
        g2d.setStroke(new BasicStroke(height*0.05f));
        g2d.draw(circle);

        circle = new Ellipse2D.Float(x + 0.2f*width, y +0.2f *height, width*0.6f, height*0.6f);
        g2d.setColor(mainColor);
        g2d.fill(circle);
        g2d.setColor(secondaryColor);
        g2d.setStroke(new BasicStroke(height*0.05f));
        g2d.draw(circle);

        circle = new Ellipse2D.Float(x + 0.3235f*width, y +0.3235f *height, width*0.353f, height*0.353f);
        g2d.setColor(centerColor);
        g2d.fill(circle);
        g2d.setColor(secondaryColor);
        g2d.setStroke(new BasicStroke(height*0.05f));
        g2d.draw(circle);
    }

    public void addPlayBoardListener(PlayBoardListener listener){
        playBoardListeners.add(listener);
    }

    private void fireOnFieldSelectedEvent(MouseEvent e){
        playBoardListeners.iterator().forEachRemaining(l -> l.onFieldClicked(getFieldId(e.getPoint())));
    }


    public boolean contains(int field, int[] toSearch){
        if(toSearch != null){
            for(int i : toSearch){
                if(i == field) return true;
            }
        }
        return false;
    }

    public byte[] getBoard() {
        return board;
    }

    public void setBoard(byte[] board) {
        this.board = board;
    }

    public int[] getHighlightFields() {
        return highlightFields;
    }

    public void setHighlightFields(int[] highlightFields) {
        this.highlightFields = highlightFields;
    }

    public int[] getLandingHighlight() {
        return landingHighlight;
    }

    public void setLandingHighlight(int[] landingHighlight) {
        this.landingHighlight = landingHighlight;
    }

    public int getStartHighlight() {
        return startHighlight;
    }

    public void setStartHighlight(int startHighlight) {
        this.startHighlight = startHighlight;
    }

    public void addHighlight(int field, Color color){
        highlights.put(field, color);
    }

    public void clearHighlights(){
        highlights.clear();
    }

}
