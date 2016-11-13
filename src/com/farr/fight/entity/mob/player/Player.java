package com.farr.fight.entity.mob.player;

import java.awt.event.KeyEvent;

import com.farr.Events.types.KeyPressedEvent;
import com.farr.Events.types.KeyReleasedEvent;
import com.farr.fight.entity.mob.Mob;

public class Player extends Mob {

	public HeroType type;
	
	private byte pressedKeys;
	private double theta;
	
	
	public enum HeroType {
		WARRIOR, MAGE, ROGUE
	}
	
	//Summons a player at some spawn point
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//I didn't know this but apparently keyPress keeps sending events.. TODO make a fix for this
	public void onKeyPress(KeyPressedEvent e) {
		System.out.println("! " + e.getKeyCode());
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_W): pressedKeys |= 0b1000; break;
		case (KeyEvent.VK_A): pressedKeys |= 0b0100; break;
		case (KeyEvent.VK_S): pressedKeys |= 0b0010; break;
		case (KeyEvent.VK_D): pressedKeys |= 0b0001; break;
		}
		
	}
	
	public void onKeyRelease(KeyReleasedEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_W): pressedKeys &= ~0b1000; break;
		case (KeyEvent.VK_A): pressedKeys &= ~0b0100; break;
		case (KeyEvent.VK_S): pressedKeys &= ~0b0010; break;
		case (KeyEvent.VK_D): pressedKeys &= ~0b0001; break;
		}
	}
	
	public void update() {
		
		pressedKeys &= 0x0F;
		switch (pressedKeys) {
		case (0b1000):
		case (0b1101): theta = -90; break;	//W pressed, W, A, D pressed
		case (0b0010): 
		case (0b0111): theta = 90; break;	//S pressed, S, A, D pressed
		case (0b0100):	
		case (0b1110): theta = 180; break;	//A pressed, A W S pressed
		case (0b0001):						//HEX 1
		case (0b1011): theta = 0; break;	//HEX b
		case (0b1100): theta = -135; break;
		case (0b1001): theta = -45; break;
		case (0b0110): theta = 135; break;
		case (0b0011): theta = 45; break;
		}
		
		if ((pressedKeys != 0b0000) && pressedKeys != 0b1111 && pressedKeys != 0b1010 && pressedKeys != 0b0101) 
			move(Math.toRadians(theta), speed);
	}
	
}
