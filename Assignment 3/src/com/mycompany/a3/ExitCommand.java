/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public ExitCommand(GameWorld gw) {
		super("Exit");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Exit command is invoked...");
		gw.exit();				// Invoke GameWorld's exit() method
		
		// Pop up dialog box to prompt player to confirm whether or not he/she wants to exit the game
		Boolean confirmExit = Dialog.show("Confirm Exit", "Do you want to exit the game?", "Yes", "No");
		
		// If player clicks yes, exit the application
		if (confirmExit) {
			Display.getInstance().exitApplication();
		}
		
	}

}
