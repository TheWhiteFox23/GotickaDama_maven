package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import java.awt.*;

public class CustomList extends JList {
    public CustomList(){
        init();
    }

    private void init() {
        setBackground(new Color(153, 158, 202));
        setForeground(new Color(66, 70, 112));
    }
}
