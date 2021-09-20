/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TickCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public TickCommand(GameWorld gw) {
		super("Tick");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Tick command is invoked...");
		gw.clockTick(20);				// Invoke GameWorld's tick method
	}
	
}
