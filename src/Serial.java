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
    // Configuration
    final private static int TIMEOUT = 2000;

    // Events
    final public static String EVENT_PAGE_START    = "page_start";
    final public static String EVENT_PAGE_COMPLETE = "page_complete";

    // Delimiters
    final private static String PAGE_START = "LT1 00";
    final private static String PAGE_END   = "ISH 00";
    final public static char CAR_RET       = '\r';
    final public static char LIN_FED       = '\n';
    final public static char LINE_END      = LIN_FED;

    // Status
    boolean pageStarted = false;

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

            switch (line.substring(0, 6)) {
                case PAGE_START:
                    Dispatcher.trigger(EVENT_PAGE_START);
                    page        = line;
                    pageStarted = true;
                    break;
                case PAGE_END:
                    if (pageStarted) {
                        Dispatcher.trigger(EVENT_PAGE_COMPLETE, page);
                        pageStarted = false;
                    }
                    break;
            }

            line = "";
        }
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
