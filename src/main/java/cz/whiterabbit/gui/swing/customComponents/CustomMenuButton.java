package cz.whiterabbit.gui.swing.customComponents;

import java.awt.*;

public class CustomMenuButton extends CustomButton{

    public CustomMenuButton(){
        init();
    }

    public CustomMenuButton(String text){
        super(text);
        init();
    }

    private void init(){
        setDefaultBackground(new Color(66, 70, 112));
        setFocusBorder(new Color(66, 70, 112));
        setDisableBackground(new Color(66, 70, 112));
        //setPreferredSize(new Dimension(150,35));
    }
}
