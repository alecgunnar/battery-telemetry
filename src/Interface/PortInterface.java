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

class PortInterface extends JOptionPane {
    public String trigger (Object[] ports) {
        if (ports == null) {
            this.showMessageDialog(null, "No serial ports available.");
            return null;
        }

        return (String) this.showInputDialog(null, "Choose a port:", "Choose a Serial Port", JOptionPane.PLAIN_MESSAGE, null, ports, null);
    }
}
