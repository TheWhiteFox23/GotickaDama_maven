package cz.whiterabbit;

import cz.whiterabbit.gui.GUIController;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        try {
            GUIController guiController = new GUIController();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
