package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class CustomArrowButton extends JButton {

    private Color focusBorder = new Color(66, 70, 112);
    private Color defaultBorder =  new Color(224,226,244);
    private Color disableBorder = new Color(153, 158, 202);


    private Color defaultBackground = new Color(66, 70, 112);
    private Color disableBackground = new Color(66, 70, 112);
    private Color focusBackground =  new Color(224,226,244);



    protected Dimension arcs = new Dimension(5, 5);
    private float stroke = 2f;

    public CustomArrowButton(){
        init();
    }

    public CustomArrowButton(String text){
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

        graphics.setColor(getForegroundColor());
        graphics.fillRoundRect(0, 0, width,
                height, arcs.width, arcs.height);

        TriangleShape triangleShape = new TriangleShape(new Point2D.Double(0+width*0.2, 0+height*0.3),
                new Point2D.Double(width - width*0.2, height*0.3), new Point2D.Double(width/2, height - height * 0.3));

        graphics.setColor(getBackgroundColor());
        graphics.fill(triangleShape);

        /*graphics.setColor(getForegroundColor());
        graphics.setStroke(new BasicStroke(stroke));
        graphics.drawRoundRect((int)stroke/2, (int)stroke/2, (int)(width-stroke*2),
                (int)(height-stroke*2), arcs.width, arcs.height);*/

        //TEXT
        /*FontMetrics fontMetrics = graphics.getFontMetrics(getFont());
        int h = fontMetrics.getHeight();
        int w = fontMetrics.stringWidth(getText());
        graphics.setColor(getForegroundColor());
        graphics.drawString(getText(), width/2 - w/2,height/2 + h/4);*/

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

    class TriangleShape extends Path2D.Double {
        public TriangleShape(Point2D... points) {
            moveTo(points[0].getX(), points[0].getY());
            lineTo(points[1].getX(), points[1].getY());
            lineTo(points[2].getX(), points[2].getY());
            closePath();
        }
    }
}
