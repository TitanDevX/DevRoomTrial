package me.titan.devroomtrial.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public enum BTimeUnit {
	SECOND(1),
	MINUTE(60),
	HOUR(60 * 60),
	DAY(60 * 60 * 24),
	WEEK(60 * 60 * 24 * 7),
	MONTH(60 * 60 * 24 * 7 *4),
	YEAR(60 * 60 * 24 * 7 * 4 * 12);

	final long length;

	BTimeUnit(long length) {
		this.length = length;
	}
	public long getDuration(int amount){
		return length * amount;
	}

	public static Object[] fromSeconds(long seconds){

		for(BTimeUnit u : Arrays.stream(values()).sorted(Comparator.reverseOrder()).collect(Collectors.toList())){
			if(seconds % u.length == 0){

				return new Object[] {u, (int) (seconds/u.length)};

			}
		}
		return null;

	}
}
