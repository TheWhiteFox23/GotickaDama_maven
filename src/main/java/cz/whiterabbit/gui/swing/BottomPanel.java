package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.customComponents.CustomButton;
import cz.whiterabbit.gui.swing.listeners.BottomPanelListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BottomPanel extends JPanel {


    List<BottomPanelListener> panelListeners;

    private CustomButton confirmButton;
    private CustomButton undoButton;
    private CustomButton redoButton;

    public BottomPanel(){
        panelListeners = new ArrayList<>();

        confirmButton = new CustomButton("Confirm");
        undoButton = new CustomButton("Undo");
        redoButton = new CustomButton("Redo");

        setOpaque(false);

        layoutComponents();
        initializeListeners();
    }

    private void initializeListeners() {
        confirmButton.addActionListener(e -> {
            panelListeners.iterator().forEachRemaining(l -> l.onConfirmClicked());
        });
        undoButton.addActionListener(e->{
            panelListeners.iterator().forEachRemaining(l -> l.onUndoClicked());
        });
        redoButton.addActionListener(e ->{
            panelListeners.iterator().forEachRemaining(l -> l.onRedoClicked());
        });
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty = 1;
        gc.weightx = 1;
        gc.insets = new Insets(0,10,0,10);

        gc.gridx=0;
        gc.weighty =0;
        add(undoButton, gc);

        gc.gridx++;
        add(confirmButton, gc);

        gc.gridx++;
        add(redoButton, gc);

    }

    public void addPanelListener(BottomPanelListener listener){
        panelListeners.add(listener);
    }

    public void setConfirmButtonEnabled(boolean enabled){
        this.confirmButton.setEnabled(enabled);
    }

    public void setConfirmButtonTest(String text) {
        this.confirmButton.setText(text);
    }
}
