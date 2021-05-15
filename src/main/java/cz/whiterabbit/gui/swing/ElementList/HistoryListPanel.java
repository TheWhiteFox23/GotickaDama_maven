package cz.whiterabbit.gui.swing.ElementList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryListPanel extends JPanel {
    private List<HistoryListListener> listenerList = new ArrayList<>();
    private List<ListCrate> historyList;
    private int selectedIndex = 0;

    public HistoryListPanel(){
        historyList = new ArrayList<>();
        layoutComponents();
        //setOpaque(false);
        setBackground(new Color(153, 158, 202));

    }

    private void layoutComponents() {
        removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty =  0.01;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy =0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        for(int i = 0; i< historyList.size(); i++){
            ListCrate c = historyList.get(i);
            addComponentToLayout(gc, c);
            gc.gridy++;
        }

        gc.weighty = 1;

        JPanel filler = new JPanel();
        filler.setOpaque(false);
        add(filler, gc);
    }

    public void setHistoryList(List<ListCrate> historyList){
        this.historyList  = historyList;
        layoutComponents();
    }

    private void addComponentToLayout(GridBagConstraints gc, ListCrate component){
        HistoryListElementImpl e = new HistoryListElementImpl(component.move, component.index);
        e.addHistoryListListener(new HistoryListListener() {
            @Override
            public void onPreviewPressed(int previewIndex) {
                listenerList.iterator().forEachRemaining(l -> l.onPreviewPressed(previewIndex));
            }

            @Override
            public void onPreviewReleased(int previewIndex) {
                listenerList.iterator().forEachRemaining(l -> l.onPreviewReleased(previewIndex));
            }

            @Override
            public void onElementClicked(int elementIndex) {
                HistoryListElementImpl oldElement =  getItemByIndex(selectedIndex);
                if(oldElement != null){
                    oldElement.setSelected(false);
                }
                selectedIndex = elementIndex;
                getItemByIndex(selectedIndex).setSelected(true);
                listenerList.iterator().forEachRemaining(l -> l.onElementClicked(elementIndex));
            }
        });
        add(e, gc);
    }

    public void addHistoryListListener(HistoryListListener listener){
        listenerList.add(listener);
    }

    private HistoryListElementImpl getItemByIndex(int index){
        Component[] components = getComponents();
        for(Component c: components){
            if(c.getClass().equals(HistoryListElementImpl.class)){
                HistoryListElementImpl e  = (HistoryListElementImpl)c;
                if(e.getIndex() == index){
                    return e;
                }
            }
        }
        return null;
    }

    public HistoryListElementImpl getSelectedItem(){
        Component[] components = getComponents();
        for(Component c: components){
            if(c.getClass().equals(HistoryListElementImpl.class)){
                HistoryListElementImpl e  = (HistoryListElementImpl)c;
                if(e.isSelected()){
                    return e;
                }
            }
        }
        return null;
    }


    public int getSelectedIndex(){
        HistoryListElementImpl selected = getSelectedItem();
        if(selected != null) {
            return getSelectedItem().getIndex();
        } else return 0;

    }


}
