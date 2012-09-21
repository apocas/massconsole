/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apocas.massconsole;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.apocas.massconsole.gui.ServerGUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author pedrodias
 */
public class Server implements Runnable {

    protected String address = null;
    protected String username = null;
    protected Connection conn;
    protected Session sess;
    protected BufferedReader input;
    protected boolean connected = false;
    protected OutputStream out;
    protected String name = "";
    protected ServerGUI sgui = null;
    protected boolean running = true;

    public Server(String address, String username, String name) {
        this.address = address;
        this.username = username;
        this.name = name;
    }

    public void connect() throws IOException {

        try {
            conn = new Connection(getAddress());
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPublicKey(this.username, new File(Config.key), Config.password);

            if (isAuthenticated == false) {
                throw new IOException("Authentication failed.");
            }

            sess = conn.openSession();
            sess.startShell();

            InputStream stdout = new StreamGobbler(sess.getStdout());
            input = new BufferedReader(new InputStreamReader(stdout));
            out = sess.getStdin();

            running = true;
            new Thread(this).start();

            connected = true;
        } catch (IOException ex) {
            connected = false;
            throw new IOException(ex.getMessage());
        }
    }

    @Override
    public void run() {
        String line = "";
        while (running) {
            try {
                if (line != null && !line.isEmpty()) {
                    sgui.getJT().append(line + "\n");
                }
                line = input.readLine();
            } catch (IOException ex) {
                connected = false;
            }
        }
    }

    public void disconnect() {
        running = false;
        if (sess != null) {
            sess.close();
        }
        if (conn != null) {
            conn.close();
        }
        connected = false;
    }

    public void sendCommand(String cmd) throws IOException {
        cmd += "\n";
        out.write(cmd.getBytes());
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @return the sgui
     */
    public ServerGUI getGUI() {
        return sgui;
    }

    /**
     * @param sgui the sgui to set
     */
    public void setGUI(ServerGUI sgui) {
        this.sgui = sgui;
    }
}
