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

class SessionHandler implements ActionListener {
    // Menu option commands
    private final String ACTION_NEW_SESS = "new_sess";
    private final String ACTION_END_SESS = "end_sess";

    // Status
    private boolean connected;
    private boolean activeSession;

    // Menu options
    private JMenuItem newSessionItem;
    private JMenuItem endSessionItem;

    // Session interface
    ISessionInterface sessIntf;

    SessionHandler (ISessionInterface intf) {
        connected = false;
        sessIntf  = intf;
    }

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

    public void initializeMenu (MainInterface mainIntf) {
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

    private void startSession () {
        
    }

    private void stopSession () {

    }

    private void updateSessionState (boolean state) {
        activeSession = state;

        newSessionItem.setEnabled(!activeSession && connected);
        endSessionItem.setEnabled(activeSession);
    }
}
