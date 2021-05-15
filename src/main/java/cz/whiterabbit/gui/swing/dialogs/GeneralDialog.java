package cz.whiterabbit.gui.swing.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;

public abstract class GeneralDialog extends JDialog {
    private Color bgColor = new Color(224,226,244);
    private Color borderColor = new Color(66, 70, 112);
    float stroke = 2f;

    public GeneralDialog(JFrame frame){
        super(frame);
        init();
    }

    /*public AbstractDarkDialog(){
        super();
        init();
    }*/

    private void init(){
        setUndecorated(true);
        setDialogMovable();
        styleDialog();
    }

    private void setDialogMovable() {
        MouseAdapter mouseAdapter = new DialogDragListener(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private void styleDialog() {
        getContentPane().setBackground(bgColor);
    }

    public boolean showDialog(){
        setLocationRelativeTo(getParent());
        setVisible(true);
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(bgColor);


        graphics.setColor(borderColor);
        graphics.setStroke(new BasicStroke(stroke));
        Shape rect = new Rectangle2D.Float(stroke/2, stroke/2, (width-stroke),
                (height-stroke));
        graphics.draw(rect);
        graphics.setStroke(new BasicStroke());;
    }
}
