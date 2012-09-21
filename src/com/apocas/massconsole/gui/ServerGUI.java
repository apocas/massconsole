/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apocas.massconsole.gui;

import com.apocas.massconsole.Server;
import java.awt.Color;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author pedrodias
 */
public class ServerGUI extends JPanel {

    protected Server serv = null;
    protected boolean running = false;
    protected int users = -1;
    protected ServerGUI instance = null;
    protected JLabel jn = new JLabel();
    protected JTextArea jt = new JTextArea();

    ServerGUI(Server serverWEB) {
        this.serv = serverWEB;
        this.serv.setGUI(this);
    }

    public void connect() throws IOException {
        this.serv.connect();
        running = true;

        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        jt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jt);

        this.add(jn);
        this.add(scrollPane);

        jn.setText(serv.getName());
    }

    public void stop() {
        running = false;
        serv.disconnect();
    }

    public JTextArea getJT() {
        return jt;
    }

    /**
     * @return the serv
     */
    public Server getServer() {
        return serv;
    }
}
