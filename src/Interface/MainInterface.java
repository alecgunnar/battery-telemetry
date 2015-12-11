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

    // Messages/labels
    private final String INITIAL_STATUS = "Please choose a serial port to begin.";

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
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
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
        GridLayout packsLayout = new GridLayout();

        packsPanel = new JPanel(packsLayout);

        contentPane.add(packsPanel);

        layout.putConstraint(SpringLayout.NORTH, packsPanel, PADDING, SpringLayout.NORTH, menuBar);
        layout.putConstraint(SpringLayout.SOUTH, packsPanel, PADDING * -4, SpringLayout.SOUTH, contentPane);
        fullWidthConstraint(packsPanel);

        packsPanel.add(new PackInterface());
        packsPanel.add(new PackInterface());
    }

    private void createStatusLabel () {
        statusLabel = new JLabel(INITIAL_STATUS);
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