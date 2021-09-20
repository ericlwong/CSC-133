/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public PositionCommand(GameWorld gw) {
		super("Position");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Position command is invoked....\n");
		gw.pressedPositionButton();			// Invoke pressedPositionButton() method in GameWorld
	}

}
