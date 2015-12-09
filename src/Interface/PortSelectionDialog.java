/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

class PortSelectionDialog extends JOptionPane {
    private Serial serialComm;
    private TelemetryInterface parent;

    PortSelectionDialog (TelemetryInterface frame, Serial comm) {
        parent     = frame;
        serialComm = comm;
    }

    public String trigger () {
        Object[] ports = serialComm.getPortNames();

        if (ports == null) {
            this.showMessageDialog(parent, "No serial ports available.");
            return null;
        }

        return (String) this.showInputDialog(parent, "Choose a port:", "Choose a Serial Port", JOptionPane.PLAIN_MESSAGE, null, ports, null);
    }
}
