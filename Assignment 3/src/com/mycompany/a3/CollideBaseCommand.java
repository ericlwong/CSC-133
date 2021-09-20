/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

public class CollideBaseCommand extends Command {
	private GameWorld gw;
	private int baseNum;
	
	// Constructor
	public CollideBaseCommand(GameWorld gw) {
		super("Collide with Base");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Collide with Base command is invoked...");
		
		// Create "OK" and "Cancel" commands
		Command ok = new Command("OK");
		Command cancel = new Command("Cancel");
		Command[] cmds = new Command[] {ok, cancel};
		
		// Create a text field for player to enter in a base
		TextField enterBaseTF = new TextField();
		Command baseConfirm = Dialog.show("Enter the base", enterBaseTF, cmds);		// Prompt dialog box to allow player to enter in a base number to collide with it
		
		// If player preses "OK", retrieve the base number entered and invoke GameWorld's baseCollision method with the base number passed as a parameter
		if (baseConfirm == ok) {
			baseNum = Integer.parseInt(enterBaseTF.getText());
			//gw.baseCollision(baseNum);
		} 
			
	}
}
