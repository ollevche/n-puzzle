package npuzzle.utils;

import npuzzle.logic.State;

import java.util.Map;
import java.util.TreeMap;

public class StateMap extends TreeMap<String, State> {
	public State put(Map.Entry<String, State> entry){
		return super.put(entry.getKey(), entry.getValue());
	}
}
