/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

interface ISubscribableDataSource {
    public String getNextPackVoltageData();
    public String getNextPackShuntCurrent();
}
