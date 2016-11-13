package com.farr.fight.entity.mob.player;

import com.farr.fight.Assets;

public class Mage extends Player {

	public Mage(int x, int y) {
		super(x, y);
		sprite = Assets.playerMage;
		speed = 1;
	}
	
}
