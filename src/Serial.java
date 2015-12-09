/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import java.util.*;
import gnu.io.*;

class Serial {
    private final int TIMEOUT = 2000;

    private HashMap ports;
    private CommPortIdentifier activePort;
    private String[] portNames = null;
    private SerialPort connection;

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

    private boolean connect (CommPortIdentifier port) {
        try {
            connection = (SerialPort) port.open("TigerControlPanel", TIMEOUT);
        } catch (Exception e) {
            connection = null;
            return false;
        }

        activePort = port;

        return true;
    }

    private void disconnect () {
        connection.close();
    }
}
