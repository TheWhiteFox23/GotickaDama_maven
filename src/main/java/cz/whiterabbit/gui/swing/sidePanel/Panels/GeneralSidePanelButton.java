package cz.whiterabbit.gui.swing.sidePanel.Panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GeneralSidePanelButton extends JButton {
    private Color basicColor = new Color(224,226,244);
    private Color focusColor = new Color(134, 139, 198);

    private Icon icon;
    private Icon selectedIcon;

    private boolean active = false;

    public GeneralSidePanelButton(Icon icon, Icon selectedIcon){
        super(icon);
        this.icon = icon;
        this.selectedIcon = selectedIcon;
        styleButton();
    }

    private void styleButton() {
        setOpaque(true);
        setBackground(basicColor);
        setBorder(new EmptyBorder(10,10,10,10));
        setModel(new FixedStateButtonModel());
        setFocusPainted(false);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(focusColor);
                setIcon(selectedIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!active){
                    setBackground(basicColor);
                    setIcon(icon);
                }
            }
        });
    }

    public GeneralSidePanelButton(String text){
        super(text);
    }

    public class FixedStateButtonModel extends DefaultButtonModel    {
        private  boolean rollover = false;

        @Override
        public boolean isPressed() {
            return false;
        }

        @Override
        public boolean isRollover() {
            return rollover;
        }

        @Override
        public void setRollover(boolean b) {
            rollover = b;
        }

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if(active){
            setBackground(focusColor);
            setIcon(selectedIcon);
        }else{
            setBackground(basicColor);
            setIcon(icon);
        }
    }
}
