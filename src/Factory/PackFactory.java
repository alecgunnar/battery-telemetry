/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

class PackFactory {
    // Message prefixes
    private final static String PACK_ONE_VOLTAGE_PREFIX = "LT1";
    private final static String PACK_TWO_VOLTAGE_PREFIX = "LT2";

    private Pack[] calcultePageData (String data) throws Exception {
        String[] lines = data.split("" + Serial.LINE_END);
        String[] vals;
        int i;

        Pack[] packs = new Pack[BatteryTelemetry.NUM_PACKS];

        for (i = 0; i < lines.length; i++) {
            vals = lines[i].split(" ");

            if (vals.length < 3)
                throw new Exception("Bad data: " + lines[i]);

            switch (vals[0]) {
                case PACK_ONE_VOLTAGE_PREFIX:
                    packs[0].putVoltage(calculateVoltage(vals[2]));
                    break;
                case PACK_TWO_VOLTAGE_PREFIX:
                    packs[1].putVoltage(calculateVoltage(vals[2]));
                    break;
            }
        }

        return packs;
    }

    private double calculateVoltage (String seed) {
        return (hexToDecimal(seed) - 512) * 0.0015;
    }

    private void calculateShuntValue (String seed) {
        // This equation is weird... Ask Dr. Brad what it means.
        // shuntValue = ((hexToDecimal(seed) - Integer.MAX_VALUE - 1) * 125) / 16777216;
    }

    private int hexToDecimal (String hex) {
        return Integer.parseInt(hex, 16);
    }
}
