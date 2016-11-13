package com.farr.fight.graphics.ui;

//Focusable interface that controls which UI element receives certain events (mainly key press events)
public interface UIFocusable {

	public boolean getFocused();
	
	public void removeFocus();
	
	public void giveFocus();
	
}
