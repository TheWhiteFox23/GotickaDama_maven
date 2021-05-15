package cz.whiterabbit.gui.swing.sidePanel.Panels;

import cz.whiterabbit.gui.swing.customComponents.*;
import cz.whiterabbit.gui.swing.sidePanel.listeners.SavePanelListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavePanel extends GeneralSidePanel{
    private List<SavePanelListener> panelListeners;

    private CustomLabel title;
    private CustomMenuButton save;
    private CustomTextField fileName;
    private CustomList saveList;
    private CustomMenuButton refreshButton;

    public SavePanel(){
        panelListeners = new ArrayList<>();

        title = new CustomLabel("<html><h1>Save</h1></html>");
        save = new CustomMenuButton("Save");
        fileName = new CustomTextField("File Name");
        saveList = new CustomList();
        refreshButton = new CustomMenuButton("Refresh");
        layoutComponents();
        initializeListeners();
        refreshList();
    }

    private void initializeListeners() {
        save.addActionListener(x->{
            if (fileName.getText().length() != 0) {
                panelListeners.iterator().forEachRemaining(l -> l.onSaveClicked(fileName.getText()));
                refreshList();
            }
        });
        refreshButton.addActionListener(l -> {
            refreshList();
        });
        saveList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String[] value = String.valueOf(saveList.getSelectedValue()).split("\\.");
                fileName.setText(value[0]);
            }
        });
    }

    private void refreshList() {
        saveList.clearSelection();
        DefaultListModel model = new DefaultListModel();
        File saveDirectory = new File("save");
        if(saveDirectory.exists()){
            File[] saveDirectoryContent = saveDirectory.listFiles();
            for(File f: saveDirectoryContent){
                model.addElement(f.getName());
            }
        }
        saveList.setModel(model);
    }


    private void layoutComponents() {
        setLayout( new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.insets = new Insets(0,10,0,10);
        gc.weighty = 0.1;
        gc.weightx = 0.1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(title, gc);


        gc.gridy++;
        gc.insets = new Insets(0,2,0,2);
        add(fileName, gc);

        gc.insets = new Insets(0,10,0,10);
        gc.gridy++;
        add(save, gc);

        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy++;
        add(new CustomScrollPane(saveList), gc);

        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        add(refreshButton, gc);
    }

    public void addPanelListener(SavePanelListener panelListener){
        panelListeners.add(panelListener);
    }


}
