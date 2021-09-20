/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public RightCommand(GameWorld gw) {
		super("Right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Steer Right command is invoked...");
		gw.steerRight();			// Invoke GameWorld's steerRight() method
	}

}
