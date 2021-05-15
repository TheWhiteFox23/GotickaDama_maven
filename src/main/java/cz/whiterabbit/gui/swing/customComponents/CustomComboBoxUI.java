package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;

public class CustomComboBoxUI extends BasicComboBoxUI {

    @Override
    protected JButton createArrowButton() {
        JButton button = new CustomArrowButton();
        button.setName("ComboBox.arrowButton");
        return  button;
    }

    @Override
    protected ComboPopup createPopup() {
        return new CustomComboPopup(super.comboBox);
    }
}
