package cz.whiterabbit.gui.swing.sidePanel.Panels;

import cz.whiterabbit.gui.swing.customComponents.CustomList;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;
import cz.whiterabbit.gui.swing.customComponents.CustomScrollPane;
import cz.whiterabbit.gui.swing.sidePanel.listeners.LoadPanelListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadPanel extends GeneralSidePanel{
    private List<LoadPanelListener> panelListeners;

    private CustomMenuButton loadButton;
    private CustomList saveList;
    private CustomMenuButton refresh;

    public  LoadPanel(){
        panelListeners = new ArrayList<>();

        loadButton = new CustomMenuButton("Load");
        saveList = new CustomList();
        refresh = new CustomMenuButton("refresh");
        layoutComponents();
        initializeListeners();
        refreshList();
    }

    private void initializeListeners() {
        loadButton.addActionListener(x ->{
            panelListeners.iterator().forEachRemaining(l -> l.onLoadFileClicked((String) saveList.getSelectedValue()));
            //load(saveList.getSelectedValue());
        });
        refresh.addActionListener(l -> {
            refreshList();
        });
    }

    private void load(Object selectedValue) {
        System.out.println(selectedValue);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(0,10,10,10);
        gc.weighty = 0.1;
        gc.weightx = 0.1;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(loadButton, gc);

        gc.gridx++;
        add(refresh, gc);

        gc.weighty = 1;
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        add(new CustomScrollPane(saveList), gc);
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

    public void addPanelListener(LoadPanelListener loadPanelListener) {
        panelListeners.add(loadPanelListener);
    }
}
