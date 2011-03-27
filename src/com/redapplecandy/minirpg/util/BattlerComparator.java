package com.redapplecandy.minirpg.util;

import java.util.Comparator;

import com.redapplecandy.minirpg.BattleEntity;

public class BattlerComparator implements Comparator<BattleEntity> {

	@Override
	public int compare(BattleEntity o1, BattleEntity o2) {
		if (o1.currentSpeed() < o2.currentSpeed()) {
			return -1;
		} else if (o1.currentSpeed() == o2.currentSpeed()) {
			return 0;
		} else {
			return 1;
		}
	}

}
