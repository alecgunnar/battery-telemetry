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
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MainInterface extends JFrame {
    // Interface configuration
    final private int WIDTH   = 800;
    final private int HEIGHT  = 600;
    final private int PADDING = 10;

    // Messages/labels
    final private String INITIAL_STATUS = "Please choose a serial port to begin.";

    // Parts of the interface
    private Container contentPane;
    private SpringLayout layout;
    private JMenuBar menuBar;
    private JPanel packsPanel;
    private JLabel statusLabel;

    MainInterface () {
        super("Battery Receiver");

        // Interface setup
        layout = new SpringLayout();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(layout);

        contentPane = getContentPane();

        // Create parts of the interface
        createMenuBar();
        createMainPanel();
        createStatusLabel();

        // Let it be
        setVisible(true);
    }

    public void addMenu (JMenu menu) {
        menuBar.add(menu);
    }

    public void updateStatus (String status) {
        statusLabel.setText(status);
    }

    private void createMenuBar () {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        layout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, contentPane);
    }

    private void createMainPanel () {
        packsPanel = new JPanel();
        contentPane.add(packsPanel);

        layout.putConstraint(SpringLayout.NORTH, packsPanel, PADDING, SpringLayout.NORTH, menuBar);
        layout.putConstraint(SpringLayout.WEST, packsPanel, PADDING, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, packsPanel, PADDING, SpringLayout.EAST, contentPane);
    }

    private void createStatusLabel () {
        statusLabel = new JLabel(INITIAL_STATUS);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        contentPane.add(statusLabel);

        layout.putConstraint(SpringLayout.SOUTH, statusLabel, PADDING * -1, SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, statusLabel, PADDING, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, statusLabel, PADDING, SpringLayout.EAST, contentPane);
    }
}