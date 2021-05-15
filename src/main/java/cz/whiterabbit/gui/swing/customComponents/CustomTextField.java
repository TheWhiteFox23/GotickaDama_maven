package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomTextField extends JTextField {
    public CustomTextField(){
        super();
        init();
    }
    public CustomTextField(String text){
        super(text);
        init();
    }

    private void init() {
        setForeground(new Color(66, 70, 112));
        setBackground(new Color(153, 158, 202));
        //setBorder(new LineBorder(new Color(153, 158, 202),2, true ));
        setBorder(new EmptyBorder(0,0,0,0));
        setPreferredSize(new Dimension(getPreferredSize().width, 30));
    }
}
