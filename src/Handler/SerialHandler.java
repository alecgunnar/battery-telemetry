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

class SerialHandler implements Listener, ActionListener {
    // Menu option commands
    final private String ACTION_CONNECT    = "connect";
    final private String ACTION_DISCONNECT = "disconnect";

    // Events
    final public static String EVENT_CONNECTION = "connection";

    // Status messages
    final private String STATUS_CONNECTED    = "Connected to serial port, waiting for data.";
    final private String STATUS_ERROR        = "Could not connect to serial port!";
    final private String STATUS_DISCONNECTED = "Disconnected from serial port.";
    final private String STATUS_PAGE_STARTED = "Started receiving a page.";
    final private String STATUS_PAGE_WAIT    = "Waiting for next page.";

    // Dependencies
    Serial serial;
    MainInterface mainIntf;
    PortInterface portIntf;

    // Status
    boolean connected;

    // Menu options
    JMenuItem connectItem;
    JMenuItem disconnectItem;

    SerialHandler (Serial comms, MainInterface mainI, PortInterface portI) {
        serial   = comms;
        mainIntf = mainI;
        portIntf = portI;

        initializeMenu();
        initializeListeners();
    }

    public void actionPerformed (ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACTION_CONNECT:
                String port = portIntf.trigger(serial.getPortNames());

                if (port != null) {
                    if (serial.setActivePort(port)) {
                        updateConnectionState(true);
                        mainIntf.updateStatus(STATUS_CONNECTED);
                    } else {
                        mainIntf.updateStatus(STATUS_ERROR);
                    }
                }
                break;
            case ACTION_DISCONNECT:
                serial.setActivePort(null);
                updateConnectionState(false);
                mainIntf.updateStatus(STATUS_DISCONNECTED);
                break;
        }
    }

    public void triggered(Event e) {
        switch (e.getEvent()) {
            case Serial.EVENT_PAGE_START:
                mainIntf.updateStatus(STATUS_PAGE_STARTED);
                break;
            case Serial.EVENT_PAGE_COMPLETE:
                mainIntf.updateStatus(STATUS_PAGE_WAIT);
                break;
        }
    }

    private void initializeMenu () {
        JMenu optionsMenu = new JMenu("Connection");
        mainIntf.addMenu(optionsMenu);

        connectItem = new JMenuItem("Connect");
        connectItem.setActionCommand(ACTION_CONNECT);
        connectItem.addActionListener(this);
        optionsMenu.add(connectItem);

        disconnectItem = new JMenuItem("Disconnect");
        disconnectItem.setActionCommand(ACTION_DISCONNECT);
        disconnectItem.addActionListener(this);
        optionsMenu.add(disconnectItem);

        updateConnectionState(false);
    }

    private void initializeListeners () {
        Dispatcher.subscribe(Serial.EVENT_PAGE_START, this);
        Dispatcher.subscribe(Serial.EVENT_PAGE_COMPLETE, this);
    }

    private void updateConnectionState (boolean state) {
        connected = state;

        Dispatcher.trigger(EVENT_CONNECTION, connected);

        connectItem.setEnabled(!connected);
        disconnectItem.setEnabled(connected);
    }
}
