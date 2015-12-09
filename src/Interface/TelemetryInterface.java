/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class TelemetryInterface extends JFrame implements ActionListener {
    // Window size
    final int WIDTH  = 800;
    final int HEIGHT = 600;

    // Menu option commands
    final String ACTION_CONNECT    = "connect";
    final String ACTION_DISCONNECT = "disconnect";

    // Messages/labels
    final String STATUS_NOT_CONNECTED = "No serial port connection.";

    // Parts of the interface
    JLabel statusMessageLabel;

    // Sub-window for choosing serial port
    PortSelectionDialog portSelDia;

    // Serial comms
    Serial serialComm;

    TelemetryInterface (Serial comm) {
        super("Battery Telemetry");

        serialComm = comm;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initializeMenu();
        initializeInterface();
    }

    public void setPortSelectionDia (PortSelectionDialog dia) {
        portSelDia = dia;
    }

    private void initializeMenu () {
        // The menu bar
        JMenuBar menuBar  = new JMenuBar();
        setJMenuBar(menuBar);

        // All the Option menu
        JMenu optionsMenu = new JMenu("Serial Options");
        menuBar.add(optionsMenu);

        // Choose serial port item
        JMenuItem connectItem = new JMenuItem("Connect");
        connectItem.setActionCommand(ACTION_CONNECT);
        connectItem.addActionListener(this);
        optionsMenu.add(connectItem);

        // Disconnect from port item
        JMenuItem disconnectItem = new JMenuItem("Disconnect");
        disconnectItem.setActionCommand(ACTION_DISCONNECT);
        disconnectItem.addActionListener(this);
        optionsMenu.add(disconnectItem);
    }

    private void initializeInterface () {
        int currPortWidth  = 800,
            currPortHeight = 30;

        // Current serial port info
        statusMessageLabel = new JLabel(STATUS_NOT_CONNECTED);
        statusMessageLabel.setBounds(0, HEIGHT - currPortHeight - 50, currPortWidth, currPortHeight);
        statusMessageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(statusMessageLabel);
    }

    public void actionPerformed (ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACTION_CONNECT:
                connectToSerialPort();
                break;
            case ACTION_DISCONNECT:
                disconnectFromSerialPort();
                break;
        }
    }

    private void connectToSerialPort () {
        String name = portSelDia.trigger();
        
        if (name == null)
            return;

        if (!serialComm.setActivePort(name)) {
            statusMessageLabel.setText("Failed to connect to serial port!");
            return;
        }

        statusMessageLabel.setText("Connected to " + name);
    }

    private void disconnectFromSerialPort () {
        serialComm.setActivePort(null);
        statusMessageLabel.setText(STATUS_NOT_CONNECTED);
    }
}
