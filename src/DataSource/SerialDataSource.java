/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import java.util.*;
import java.io.InputStream;
import gnu.io.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class SerialDataSource implements SerialPortEventListener, ActionListener, ISubscribableDataSource {
    // Menu option commands
    private final String ACTION_CONNECT    = "connect";
    private final String ACTION_DISCONNECT = "disconnect";

    // Configuration
    final private static int TIMEOUT = 2000;

    // Delimiters
    final private static String PAGE_START = "LT1 00";
    final private static String PAGE_END   = "ISH 00";
    final private static int LINE_HEAD_LEN = 6;
    final public static char CAR_RET       = '\r';
    final public static char LIN_FED       = '\n';
    final public static char LINE_END      = LIN_FED;

    // Port selection window
    private PortInterface portIntf;

    // Status
    private boolean pageStarted = false;

    // Available ports
    private HashMap ports = new HashMap();
    private String[] portNames = null;

    // Serial connection
    private CommPortIdentifier activePort;
    private InputStream input;
    private SerialPort connection = null;

    // Page and line data
    private String line = "";
    private String page = "";

    // Menu items
    private JMenuItem connectItem;
    private JMenuItem disconnectItem;

    SerialDataSource (PortInterface intf) {
        portIntf = intf;
    }

    public HashMap getPorts () {
        Enumeration allPorts = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier p;

        ports.clear();

        while (allPorts.hasMoreElements()) {
            p = (CommPortIdentifier) allPorts.nextElement();

            if (p.getPortType() == CommPortIdentifier.PORT_SERIAL)
                ports.put(p.getName(), p);
        }

        return ports;
    }

    public String[] getPortNames () {
        getPorts();

        portNames = new String[ports.size()];

        Iterator i = ports.entrySet().iterator();
        int index  = 0;
        Map.Entry e;

        while (i.hasNext()) {
            e = (Map.Entry) i.next();

            portNames[index++] = (String) e.getKey();
        }

        return portNames;
    }

    public boolean setActivePort (String name) {
        if (name == null) {
            if (connection != null)
                disconnect();

            return true;
        }

        return connect((CommPortIdentifier) ports.get(name));
    }

    public String getActivePort () {
        if (activePort == null)
            return null;

        return activePort.getName();
    }

    public void serialEvent(SerialPortEvent e) {
        if (e.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                while (input.available() > 0)
                    putChar((char) ((int) input.read()));
            } catch (Exception exc) { }
        }
    }

    private void putChar (char next) {
        if (next == CAR_RET)
            return;

        line += next;

        if (next == LINE_END) {
            if (pageStarted)
                page += line;

            switch (line.substring(0, LINE_HEAD_LEN)) {
                case PAGE_START:
                    page        = line;
                    pageStarted = true;
                    break;
                case PAGE_END:
                    if (pageStarted)
                        pageStarted = false;
                    break;
            }

            line = "";
        }
    }

    public String getNextPackVoltageData () {
        return "";
    }

    public String getNextPackShuntCurrent () {
        return "";
    }

    public void actionPerformed (ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACTION_CONNECT:
                String port = portIntf.trigger(getPortNames());

                if (port != null)
                    setActivePort(port);
                break;
            case ACTION_DISCONNECT:
                setActivePort(null);
                break;
        }
    }

    public void initializeMenu (MainInterface mainIntf) {
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
    }

    private boolean connect (CommPortIdentifier port) {
        try {
            connection = (SerialPort) port.open("BatteryReceiverPanel", TIMEOUT);

            connection.addEventListener(this);
            connection.notifyOnDataAvailable(true);
            connection.setSerialPortParams(38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            input = connection.getInputStream();
        } catch (Exception e) {
            disconnect();
            return false;
        }

        activePort = port;

        return true;
    }

    private void disconnect () {
        if (connection != null) {
            connection.removeEventListener();
            connection.close();
            connection = null;
        }

        if (input != null) {
            try {
                input.close();
            } catch (Exception e) { }
        }

        input = null;
    }
}
