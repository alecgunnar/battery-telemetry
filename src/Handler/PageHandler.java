/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

class PageHandler implements Listener {
    // Events
    public final static String EVENT_NEW_PAGE = "new_page";

    // Message prefixes
    private final static String PACK_ONE_VOLTAGE_PREFIX = "LT1";
    private final static String PACK_TWO_VOLTAGE_PREFIX = "LT2";

    // Current page
    private Pack[] packs;

    PageHandler () {
        initializeListeners();
    }

    public void triggered (Event e) {
        switch (e.getEvent()) {
            case Serial.EVENT_PAGE_COMPLETE:
                packs = new Pack[2];

                packs[0] = new Pack();
                packs[1] = new Pack();

                try {
                    calcultePageData((String) e.getData());
                    Dispatcher.trigger(EVENT_NEW_PAGE, packs);
                } catch (Exception exc) { }
        }
    }

    private void initializeListeners () {
        Dispatcher.subscribe(Serial.EVENT_PAGE_COMPLETE, this);
    }

    private void calcultePageData (String data) throws Exception {
        String[] lines = data.split("" + Serial.LINE_END);
        String[] vals;
        int i;

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
    }

    private double calculateVoltage (String seed) {
        return (hexToDecimal(seed) - 512) * 0.0015;
    }

    private int hexToDecimal (String hex) {
        return Integer.parseInt(hex, 16);
    }
}
