/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apocas.massconsole.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author pedrodias
 */
public class GUIDispatcher implements Runnable {

    private JPanel panel;
    private ArrayList<ServerGUI> servers;

    public GUIDispatcher(JPanel panel, ArrayList<ServerGUI> servers) {
        this.panel = panel;
        this.servers = servers;
    }

    @Override
    public void run() {
        for (ServerGUI s : servers) {
            try {
                s.connect();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }

                panel.add(s);
                panel.revalidate();
            } catch (IOException ex) {
                String ss = ex.getMessage();
                if (ex.getMessage().equals("Publickey authentication failed.")) {
                    JOptionPane.showMessageDialog(null, "Password failed!");
                    System.exit(0);
                }

                //servers.remove(s);
                //Logger.getLogger(GUIDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
