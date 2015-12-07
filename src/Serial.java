/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import java.util.Enumeration;
import gnu.io.*;

class Serial {
    private static Enumeration ports = null;

    /**
     * With help from:
     * https://www.henrypoon.com/blog/2011/01/01/serial-communication-in-java-with-example-program/
     */
    public static void listAllPorts () {
        System.out.println("Here are all of the serial ports:");

        ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();

            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(curPort.getName());
            }
        }
    }
}
