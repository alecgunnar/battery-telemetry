/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

class Pack {
    // Default values
    public final static double DEFAULT_MAX_VOLTAGE = -5.0;
    public final static double DEFAULT_MIN_VOLTAGE = 5.0;

    // Max, min, and diff
    private double maxVoltage = DEFAULT_MAX_VOLTAGE;
    private double minVoltage = DEFAULT_MIN_VOLTAGE;
    private double avgVoltage = 0.0;
    private double totVoltage = 0.0;
    private int numVoltages   = 0;

    public double getMinVoltage () {
        return minVoltage;
    }

    public void setMinVoltage (double minV) {
        minVoltage = minV;
    }

    public double getMaxVoltage () {
        return maxVoltage;
    }

    public void setMaxVoltage (double maxV) {
        maxVoltage = maxV;
    }

    public double getDiffVoltage () {
        return maxVoltage - minVoltage;
    }

    public int getNumVoltages () {
        return numVoltages;
    }

    public double getTotalVoltage () {
        return totVoltage;
    }

    public double getAverageVoltage () {
        return avgVoltage;
    }

    public void putVoltage (double voltage) {
        if (voltage < minVoltage)
            minVoltage = voltage;

        if (voltage > maxVoltage)
            maxVoltage = voltage;

        totVoltage += voltage;
        numVoltages++;

        avgVoltage = totVoltage / numVoltages;
    }
}
