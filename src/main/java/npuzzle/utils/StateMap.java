package npuzzle.utils;

import npuzzle.logic.State;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class StateMap extends TreeMap<String, State> {

	public State put(Map.Entry<String, State> entry){
		return super.put(entry.getKey(), entry.getValue());
	}

	public void removeUnsolvable() {

		Iterator<Map.Entry<String, State>> iter = this.entrySet().iterator();
		Map.Entry<String, State> current;

		while (iter.hasNext()) {
			current = iter.next();
			if (!current.getValue().isSolvable())
				iter.remove();
		}
	}
}
