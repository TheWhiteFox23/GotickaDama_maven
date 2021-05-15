package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomComboBox extends JComboBox {

    public CustomComboBox(){
        super();
        init();
    }

    public CustomComboBox(DefaultComboBoxModel modelPlayerTypeBlack) {
        super(modelPlayerTypeBlack);
        init();
    }

    private void init(){
        setUI(new CustomComboBoxUI());
        setBackground(new Color(224,226,244));
        setFocusable(false);
        setForeground(new Color(66, 70, 112));
        //setBorder(new EmptyBorder(0,0,0,0));
    }


}
