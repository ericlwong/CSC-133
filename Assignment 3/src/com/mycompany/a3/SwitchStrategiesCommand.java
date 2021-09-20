/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SwitchStrategiesCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public SwitchStrategiesCommand(GameWorld gw) {
		super("Switch Strategies");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Switch Strategies command is invoked...");
		gw.switchStrategies();				// Invoke GameWorld's switchStrategies() method which contains the logic to switch the NPC Cyborgs' strategies
	}

}
