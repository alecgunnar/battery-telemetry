/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

class Dispatcher {
	private static HashMap<String, ArrayList<Listener>> events = new HashMap();

	public static void register (String name) {
		events.put(name, new ArrayList<Listener>());
	}

	public static void trigger (String name, Object data) {
		if (!events.containsKey(name)) {
			register(name);
			return;
		}

		Iterator i = events.get(name).iterator();
		Event e    = new Event(name, data);

		while (i.hasNext())
			((Listener) i.next()).triggered(e);
	}

	public static void trigger (String name) {
		trigger(name, null);
	}

	public static void subscribe (String name, Listener listener) {
		if (!events.containsKey(name))
			register(name);

		events.get(name).add(listener);
	}
}