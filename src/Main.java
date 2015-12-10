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
        Serial serialComm      = new Serial();
        MainInterface mainIntf = new MainInterface();
        PortInterface portIntf = new PortInterface();

        SerialHandler serialHandler   = new SerialHandler(serialComm, mainIntf, portIntf);
        SessionHandler sessionHandler = new SessionHandler(mainIntf);

        mainIntf.setVisible(true);
    }
}
