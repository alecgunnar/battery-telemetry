/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

class Main {
    public static void main (String[] args) {
        Serial serialComm              = new Serial();
        TelemetryInterface telIntf     = new TelemetryInterface(serialComm);
        PortSelectionDialog portSelDia = new PortSelectionDialog(telIntf, serialComm);

        telIntf.setPortSelectionDia(portSelDia);
        telIntf.setVisible(true);
    }
}
