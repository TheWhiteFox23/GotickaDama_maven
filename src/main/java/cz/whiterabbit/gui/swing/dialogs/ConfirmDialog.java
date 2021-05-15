package cz.whiterabbit.gui.swing.dialogs;

import cz.whiterabbit.gui.swing.customComponents.CustomButton;
import cz.whiterabbit.gui.swing.customComponents.CustomLabel;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;

import javax.swing.*;
import java.awt.*;

public class ConfirmDialog extends GeneralDialog{

    //OTHERS
    private String text;
    private boolean response;

    //COMPONENTS
    private JButton confirmJButton;
    private JButton cancelJButton;
    private JLabel textJLabel;

    public ConfirmDialog(JFrame frame, String text){
        super(frame);
        this.text = text;
        init();

    }

    private void init() {
        setSize(new Dimension(300,150));
        setModal(true);

        confirmJButton = new CustomMenuButton("OK");
        cancelJButton = new CustomMenuButton("Cancel");
        textJLabel = new CustomLabel(text);

        layoutComponents();
        initializeListeners();
    }


    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //LEVEL 0
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;

        //textJLabel
        gc.gridwidth = 0;
        add(textJLabel, gc);

        //LEVEL 1
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(5,30,5,30);
        gc.gridy = 1;
        gc.gridwidth = 1;
        add(confirmJButton, gc);

        gc.gridx = 1;
        add(cancelJButton, gc);
    }

    private void initializeListeners(){
        cancelJButton.addActionListener(e -> {
            response = false;
            dispose();
        });
        confirmJButton.addActionListener(e -> {
            response = true;
            dispose();
        });
    }
}
