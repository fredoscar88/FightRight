package com.farr.fight.graphics.ui;

import com.farr.Events.types.KeyTypedEvent;

//Focusable interface that controls which UI element receives certain events (mainly key press events)
/**
 * Interface designed to manage which UIComponents receive KeyEvents (press and release), by distributing events only to the element that implements this interface and is focused.
 * In implementing this interface it is important to not let more than one element have focus (unless that is desirable behavior);
 * @author Henry
 *
 */
interface UIFocusable {

	public boolean getFocused();
	
	public void removeFocus();
	
	public void giveFocus();
	
//	public boolean onKeyPress(KeyPressedEvent e);
	
//	public boolean onKeyRelease(KeyReleasedEvent e);
	
	public boolean onKeyType(KeyTypedEvent e);
	
}
