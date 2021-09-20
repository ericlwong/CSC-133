/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent; 

public class SoundCommand extends Command {
	private GameWorld gw;
	
	// Constructor
	public SoundCommand(GameWorld gw) {
		super("Sound");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Sound command is invoked...");
		gw.setSound();				// Invoke GameWorld's setSound() method
	}

}
