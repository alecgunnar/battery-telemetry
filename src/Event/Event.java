/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

class Event {
	private String event;
	private Object data;

	Event (String e, Object d) {
		event = e;
		data  = d;
	}

	public String getEvent () {
		return event;
	}

	public Object getData () {
		return data;
	}
}
