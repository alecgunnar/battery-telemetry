/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.SpringLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;

import agc.events.*;

class PackInterface extends JPanel {
    // Instances
    private final static PackInterface instance1 = new PackInterface(1);
    private final static PackInterface instance2 = new PackInterface(2);

    public final static String PACK1_INSTANCE = "inst_1";
    public final static String PACK2_INSTANCE = "inst_2";

    // Pack information
    private final int packNumber;

    // Field labels
    private final String MAX_VOLTAGE  = "Maximum Cell Voltage:";
    private final String MIN_VOLTAGE  = "Minimum Cell Voltage:";
    private final String DIFF_VOLTAGE = "Voltage Difference:";
    private final String TOT_VOLTAGE  = "Total Voltage:";
    private final String AVG_VOLTAGE  = "Average Voltage:";

    // Other values
    private final String VOLT_UNIT_ABBR  = " V";
    private final String INITIAL_VOLTAGE = "0.0" + VOLT_UNIT_ABBR;

    // Layout
    private SpringLayout layout;
    private Component prevLabel = this;
    private Component prevValue = this;

    // Data files
    private JLabel maxVoltage;
    private JLabel minVoltage;
    private JLabel diffVoltage;
    private JLabel totVoltage;
    private JLabel avgVoltage;

    public static PackInterface getInstance (String which) {
        if (which == PACK1_INSTANCE)
            return instance1;

        return instance2;
    }

    private PackInterface (int num) {
        packNumber = num;
        layout     = new SpringLayout();

        setBorder(BorderFactory.createTitledBorder("  Pack " + packNumber + "  "));
        setLayout(layout);

        createDataFields();
    }

    private void createDataFields () {
        maxVoltage  = new JLabel(INITIAL_VOLTAGE);
        minVoltage  = new JLabel(INITIAL_VOLTAGE);
        diffVoltage = new JLabel(INITIAL_VOLTAGE);
        totVoltage  = new JLabel(INITIAL_VOLTAGE);
        avgVoltage  = new JLabel(INITIAL_VOLTAGE);

        addDataField(new JLabel(MAX_VOLTAGE), maxVoltage);
        addDataField(new JLabel(MIN_VOLTAGE), minVoltage);
        addDataField(new JLabel(DIFF_VOLTAGE), diffVoltage);
        addDataField(new JLabel(TOT_VOLTAGE), totVoltage);
        addDataField(new JLabel(AVG_VOLTAGE), avgVoltage);
    }

    private void addDataField (JLabel label, JLabel value) {
        add(label);
        add(value);

        labelConstraint(label, prevLabel);
        valueConstraint(value, prevValue);

        prevLabel = label;
        prevValue = value;
    }

    private void labelConstraint (Component comp, Component above) {
        int paddTop = paddingTop(above);

        layout.putConstraint(SpringLayout.NORTH, comp, paddTop, SpringLayout.NORTH, above);
        layout.putConstraint(SpringLayout.WEST, comp, MainInterface.PADDING, SpringLayout.WEST, this);
    }

    private void valueConstraint (Component comp, Component above) {
        int paddTop = paddingTop(above);

        layout.putConstraint(SpringLayout.NORTH, comp, paddTop, SpringLayout.NORTH, above);
        layout.putConstraint(SpringLayout.EAST, comp, MainInterface.PADDING * -1, SpringLayout.EAST, this);
    }

    private int paddingTop (Component comp) {
        int paddTop = MainInterface.PADDING * 2;

        if (comp != this)
            paddTop *= 2;

        return paddTop;
    }
}
