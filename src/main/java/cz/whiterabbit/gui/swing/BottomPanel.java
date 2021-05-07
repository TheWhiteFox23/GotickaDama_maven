package cz.whiterabbit.gui.swing;

import cz.whiterabbit.gui.swing.listeners.BottomPanelListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BottomPanel extends JPanel {
    List<BottomPanelListener> panelListeners;

    private JButton confirmButton;
    private JButton undoButton;
    private JButton redoButton;

    public BottomPanel(){
        panelListeners = new ArrayList<>();

        confirmButton = new JButton("Confirm");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
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
