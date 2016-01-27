/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MainInterface extends JFrame {
    // Interface configuration
    private final int WIDTH         = 800;
    private final int HEIGHT        = 600;
    public final static int PADDING = 10;

    // Status messages
    private final String STATUS_INITIAL      = "Please choose a serial port to begin.";
    private final String STATUS_CONNECTED    = "Connected to serial port, waiting for data.";
    private final String STATUS_ERROR        = "Could not connect to serial port.";
    private final String STATUS_DISCONNECTED = "Disconnected from serial port.";
    private final String STATUS_PAGE_STARTED = "Receiving data...";
    private final String STATUS_PAGE_WAIT    = "Waiting for more data...";

    // Parts of the interface
    private Container contentPane;
    private SpringLayout layout;
    private JMenuBar menuBar;
    private JPanel packsPanel;
    private JLabel statusLabel;

    private PackInterface[] packs = new PackInterface[BatteryTelemetry.NUM_PACKS];

    MainInterface () {
        super("Battery Receiver");

        // Set the layout
        layout = new SpringLayout();
        setLayout(layout);

        contentPane = getContentPane();

        // Create the pack panels
        packs[0] = new PackInterface();
        packs[1] = new PackInterface();

        // Set other options
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        buildInterface();
    }

    public void addMenu (JMenu menu) {
        menuBar.add(menu);
    }

    public void updatePackData (int which, Pack data) {

    }

    public void updateShuntCurrent (double current) {

    }

    public void updateStatus (String status) {
        statusLabel.setText(status);
    }

    private void buildInterface () {
        createMenuBar();
        createMainPanel();
        createShuntCurrentPanel();
        createStatusLabel();
    }

    private void createMenuBar () {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        layout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, contentPane);
    }

    private void createMainPanel () {
        GridLayout packsLayout = new GridLayout();

        packsPanel = new JPanel(packsLayout);

        contentPane.add(packsPanel);

        layout.putConstraint(SpringLayout.NORTH, packsPanel, PADDING, SpringLayout.NORTH, menuBar);
        layout.putConstraint(SpringLayout.SOUTH, packsPanel, -1 * (HEIGHT / 2), SpringLayout.SOUTH, contentPane);
        fullWidthConstraint(packsPanel);

        packsPanel.add(packs[0]);
        packsPanel.add(packs[1]);
    }

    private void createShuntCurrentPanel () {
        
    }

    private void createStatusLabel () {
        statusLabel = new JLabel(STATUS_INITIAL);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        contentPane.add(statusLabel);

        layout.putConstraint(SpringLayout.NORTH, statusLabel, PADDING, SpringLayout.SOUTH, packsPanel);
        fullWidthConstraint(statusLabel);
    }

    private void fullWidthConstraint (Component comp) {
        layout.putConstraint(SpringLayout.WEST, comp, PADDING, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, comp, PADDING * -1, SpringLayout.EAST, contentPane);
    }
}
