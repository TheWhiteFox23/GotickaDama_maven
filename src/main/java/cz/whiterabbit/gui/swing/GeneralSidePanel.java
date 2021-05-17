package cz.whiterabbit.gui.swing;

import javax.swing.*;
import java.awt.*;

public abstract class GeneralSidePanel extends JPanel {

    private Color bgColor = new Color(224,226,244);
    private Color borderColor = new Color(66,70,112);
    protected Dimension arcs = new Dimension(30, 30);

    public GeneralSidePanel(){
        setVisible(false);
        setOpaque(false);
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
        graphics.fillRoundRect(0, 0, width,
                height, arcs.width, arcs.height);

        graphics.setColor(borderColor);
        graphics.setStroke(new BasicStroke(2f));
        graphics.drawRoundRect(2, 2, width -4,
                height-4, arcs.width, arcs.height);

        graphics.setStroke(new BasicStroke());
    }
}
