package cz.whiterabbit.gui.swing;

import javax.swing.*;
import java.awt.*;

public class RoundCornerPanel extends JPanel {
    private JComponent centerComponent;

    private Color bgColor = new Color(224,226,244);
    protected Dimension arcs = new Dimension(30, 30);


    public RoundCornerPanel(JComponent centerComponent){
        this.centerComponent = centerComponent;
        setOpaque(false);
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty = 1;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(20,20,20,20);

        if(centerComponent != null)add(centerComponent, gc);

        gc.gridy++;
        gc.weighty = 0.01;
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

        graphics.setStroke(new BasicStroke());

        //centerComponent.paint(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Container c = getParent();
        int width = c.getWidth();
        int height = c.getHeight();
        int dimension = (width < height ? width:height);
        return new Dimension(dimension, dimension );
    }
}
