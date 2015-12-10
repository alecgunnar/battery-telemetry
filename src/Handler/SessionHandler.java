/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

class SessionHandler implements ActionListener, Listener {
    // Menu option commands
    final private String ACTION_NEW_SESS = "new_sess";
    final private String ACTION_END_SESS = "end_sess";

    // Dependencies
    private MainInterface mainIntf;

    // Status
    private boolean connected;
    private boolean activeSession;

    // Session
    private File sessionFile;

    // Menu options
    private JMenuItem newSessionItem;
    private JMenuItem endSessionItem;

    // File chooser
    private JFileChooser fileChooser = new JFileChooser();

    SessionHandler (MainInterface mainI) {
        mainIntf  = mainI;
        connected = false;

        initializeMenu();
        initializeListeners();
    }

    // Menu events
    public void actionPerformed (ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACTION_NEW_SESS:
                startSession();
                break;
            case ACTION_END_SESS:
                stopSession();
                break;
        }
    }

    // Global events
    public void triggered(Event e) {
        switch (e.getEvent()) {
            case SerialHandler.EVENT_CONNECTION:
                connected = (boolean) e.getData();

                if (activeSession && !connected)
                    stopSession();

                updateSessionState(activeSession);
                break;
        }
    }

    private void initializeMenu () {
        JMenu optionsMenu = new JMenu("Session");
        mainIntf.addMenu(optionsMenu);

        newSessionItem = new JMenuItem("Start");
        newSessionItem.setActionCommand(ACTION_NEW_SESS);
        newSessionItem.addActionListener(this);
        optionsMenu.add(newSessionItem);

        endSessionItem = new JMenuItem("Stop");
        endSessionItem.setActionCommand(ACTION_END_SESS);
        endSessionItem.addActionListener(this);
        optionsMenu.add(endSessionItem);
        updateSessionState(false);
    }

    private void initializeListeners () {
        Dispatcher.subscribe(SerialHandler.EVENT_CONNECTION, this);
    }

    private void startSession () {
        int ret = fileChooser.showSaveDialog(mainIntf);

        if (ret == JFileChooser.APPROVE_OPTION) {
            sessionFile = fileChooser.getSelectedFile();

            try {
                updateSessionState(sessionFile.createNewFile());
            } catch (IOException e) { }
        }
    }

    private void stopSession () {
        if (!activeSession)
            return;

        sessionFile = null;

        updateSessionState(false);
    }

    private void updateSessionState (boolean state) {
        activeSession = state;

        newSessionItem.setEnabled(!activeSession && connected);
        endSessionItem.setEnabled(activeSession);
    }
}
