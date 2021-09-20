/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public BrakeCommand(GameWorld gw) {
		super("Brake");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Brake command is invoked...");
		gw.brake();				// Invoke GameWorld's brake() method
	}

}
