/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.SwingUtilities;

class BatteryTelemetry {
    public final static int NUM_PACKS = 2;

    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                MainInterface mainIntf    = new MainInterface();
                SessionInterface sessIntf = new SessionInterface(mainIntf);
                PortInterface portIntf    = new PortInterface();

                SerialDataSource serialSrc = new SerialDataSource(portIntf);
                DataHandler dataHandler    = new DataHandler(serialSrc);

                SessionHandler sessHandler = new SessionHandler(sessIntf);

                serialSrc.initializeMenu(mainIntf);
                sessHandler.initializeMenu(mainIntf);

                mainIntf.setVisible(true);
            }
        });
    }
}
