package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CustomComboPopup extends BasicComboPopup {

    /**
     * Constructs a new instance of {@code BasicComboPopup}.
     *
     * @param combo an instance of {@code JComboBox}
     */
    public CustomComboPopup(JComboBox<Object> combo) {
        super(combo);
        setBorder(new EmptyBorder(0,0,0,0));
        JList list = getList();
        list.setBackground(new Color(153, 158, 202));
        list.setForeground(new Color(66, 70, 112));
        list.setSelectionBackground(new Color(66, 70, 112));
        list.setSelectionForeground(new Color(224,226,244));
    }
}
