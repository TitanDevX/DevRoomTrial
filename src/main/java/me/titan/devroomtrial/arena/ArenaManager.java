package me.titan.devroomtrial.arena;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArenaManager {

	// Current ongoing arena battles, per player UUID
	Map<UUID, ArenaBattle> battles = new HashMap<>();


	public Map<UUID, ArenaBattle> getBattles() {
		return battles;
	}
}
