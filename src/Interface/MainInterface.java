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

class MainInterface extends JFrame {
    // Window size
    final int WIDTH  = 800;
    final int HEIGHT = 600;

    // Messages/labels
    final String INITIAL_STATUS = "Ready to go...";

    // Parts of the interface
    JLabel statusLabel;
    JMenuBar menuBar;

    MainInterface () {
        super("Battery Receiver");

        // Interface setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        createMenuBar();
        createStatusLabel();
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
    }

    private void createStatusLabel () {
        int labelWidth  = 800,
            labelHeight = 30;

        statusLabel = new JLabel(INITIAL_STATUS);
        statusLabel.setBounds(0, HEIGHT - labelHeight - 50, labelWidth, labelHeight);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(statusLabel);
    }
}