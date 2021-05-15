package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JButton {
    private Color focusBorder = new Color(83, 92, 178);
    private Color defaultBorder =  new Color(224,226,244);
    private Color disableBorder = new Color(153, 158, 202);


    private Color defaultBackground = new Color(83, 92, 178);
    private Color disableBackground = new Color(83, 92, 178);
    private Color focusBackground =  new Color(224,226,244);



    protected Dimension arcs = new Dimension(10, 10);
    private float stroke = 2f;

    public CustomButton(){
        init();
    }

    public CustomButton(String text){
        super(text);
        init();
    }

    private void init() {
        setOpaque(false);
        setFocusPainted(false);
        setFocusable(false);
        setRolloverEnabled(false);
        //setBorder(new EmptyBorder(10,10,10,10));
        setBorder(new EmptyBorder(10,30,10,30));
        initializeListeners();
        //setPreferredSize(new Dimension(100, 35));
    }

    private void initializeListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();

            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(getBackgroundColor());
        graphics.fillRoundRect((int)stroke/2, (int)stroke/2, (int)(width-stroke*2),
                (int)(height-stroke*2), arcs.width, arcs.height);

        graphics.setColor(getForegroundColor());
        graphics.setStroke(new BasicStroke(stroke));
        graphics.drawRoundRect((int)stroke/2, (int)stroke/2, (int)(width-stroke*2),
                (int)(height-stroke*2), arcs.width, arcs.height);

        //TEXT
        FontMetrics fontMetrics = graphics.getFontMetrics(getFont());
        int h = fontMetrics.getHeight();
        int w = fontMetrics.stringWidth(getText());
        graphics.setColor(getForegroundColor());
        graphics.drawString(getText(), width/2 - w/2,height/2 + h/4);

        graphics.setStroke(new BasicStroke());
    }

    private Color getBackgroundColor(){
        if(!isEnabled()){
            return disableBackground;
        }else if (getMousePosition() != null){
            return focusBackground;
        }else{
            return defaultBackground;
        }
    }

    private Color getForegroundColor(){
        if(!isEnabled()){
            return disableBorder;
        }else if (getMousePosition() != null){
            return focusBorder;
        }else{
            return defaultBorder;
        }
    }

    public Color getFocusBorder() {
        return focusBorder;
    }

    public void setFocusBorder(Color focusBorder) {
        this.focusBorder = focusBorder;
    }

    public Color getDefaultBorder() {
        return defaultBorder;
    }

    public void setDefaultBorder(Color defaultBorder) {
        this.defaultBorder = defaultBorder;
    }

    public Color getDisableBorder() {
        return disableBorder;
    }

    public void setDisableBorder(Color disableBorder) {
        this.disableBorder = disableBorder;
    }

    public Color getDefaultBackground() {
        return defaultBackground;
    }

    public void setDefaultBackground(Color defaultBackground) {
        this.defaultBackground = defaultBackground;
    }

    public Color getDisableBackground() {
        return disableBackground;
    }

    public void setDisableBackground(Color disableBackground) {
        this.disableBackground = disableBackground;
    }

    public Color getFocusBackground() {
        return focusBackground;
    }

    public void setFocusBackground(Color focusBackground) {
        this.focusBackground = focusBackground;
    }
}
