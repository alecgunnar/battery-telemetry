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

class Serial implements SerialPortEventListener {
    private final int TIMEOUT = 2000;

    private HashMap ports;
    private CommPortIdentifier activePort;
    private String[] portNames = null;
    private SerialPort connection;
    private InputStream input;

    Serial () {
        ports      = new HashMap();
        connection = null;
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
        Map.Entry e;
        int index = 0;

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

    public String getActivePortName () {
        if (activePort == null)
            return null;

        return activePort.getName();
    }

    public void serialEvent(SerialPortEvent e) {
        if (e.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            int character;
            try {
                character = input.read();
                System.out.print((char) character);
            } catch (Exception exc) { }
        }
    }

    private boolean connect (CommPortIdentifier port) {
        disconnect();

        try {
            connection = (SerialPort) port.open("TigerControlPanel", TIMEOUT);

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
