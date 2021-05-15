package cz.whiterabbit.gui.swing.sidePanel.Panels;

import javax.swing.*;
import java.awt.*;

public abstract class GeneralSidePanel extends JPanel {

    private Color bgColor = new Color(224,226,244);
    private Color borderColor = new Color(134, 139, 198);
    protected Dimension arcs = new Dimension(30, 30);
    private float stroke = 2f;

    public GeneralSidePanel(){
        setVisible(false);
        setOpaque(false);
        setPreferredSize(new Dimension(200, getPreferredSize().height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        graphics.fillRoundRect((int)stroke/2, (int)stroke/2, (int)(width-stroke*2),
                (int)(height-stroke*2), arcs.width, arcs.height);

        graphics.setColor(borderColor);
        graphics.setStroke(new BasicStroke(stroke));
        graphics.drawRoundRect((int)stroke/2, (int)stroke/2, (int)(width-stroke*2),
                (int)(height-stroke*2), arcs.width, arcs.height);
        graphics.setStroke(new BasicStroke());
    }
}
