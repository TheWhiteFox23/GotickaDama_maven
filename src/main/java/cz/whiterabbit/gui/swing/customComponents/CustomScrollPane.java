package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomScrollPane extends JScrollPane {

    public CustomScrollPane(JComponent component){
        super(component);
        init();
    }

    private void init() {
        setBorder(new EmptyBorder(0,0,0,0));
        setComponentZOrder(getVerticalScrollBar(), 0);
        setComponentZOrder(getViewport(), 1);
        getVerticalScrollBar().setOpaque(false);

        setLayout(new ScrollPaneLayout() {
            @Override
            public void layoutContainer(Container parent) {
                JScrollPane scrollPane = (JScrollPane) parent;

                Rectangle availR = scrollPane.getBounds();
                availR.x = availR.y = 0;

                Insets parentInsets = parent.getInsets();
                availR.x = parentInsets.left;
                availR.y = parentInsets.top;
                availR.width -= parentInsets.left + parentInsets.right;
                availR.height -= parentInsets.top + parentInsets.bottom;

                Rectangle vsbR = new Rectangle();
                vsbR.width = 12;
                vsbR.height = availR.height;
                vsbR.x = availR.x + availR.width - vsbR.width;
                vsbR.y = availR.y;

                if (viewport != null) {
                    viewport.setBounds(availR);
                }
                if (vsb != null) {
                    vsb.setVisible(true);
                    vsb.setBounds(vsbR);
                }
            }
        });
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
    }
}
