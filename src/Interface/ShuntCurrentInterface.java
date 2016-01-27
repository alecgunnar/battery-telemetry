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

class ShuntCurrentInterface extends JPanel implements Subscriber {
    ShuntCurrentInterface () {

    }

    public void initializeSubscriber (Serial comm) {
        comm.subscribe(PageHandler.EVENT_NEW_PACKS, this);
    }

    public void notify (Broadcast msg) {
        switch (msg.getType()) {
            
        }
    }
}
