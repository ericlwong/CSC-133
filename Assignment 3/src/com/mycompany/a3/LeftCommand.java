/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public LeftCommand(GameWorld gw) {
		super("Left");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Steer Left command is invoked...");
		gw.steerLeft();			// Invoke GameWorld's steerLeft() method
	}

}
