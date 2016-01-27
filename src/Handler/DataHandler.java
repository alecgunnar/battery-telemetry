/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

class DataHandler {
    // Data source
    private ISubscribableDataSource source;

    DataHandler (ISubscribableDataSource src) {
        source = src;
    }
}
