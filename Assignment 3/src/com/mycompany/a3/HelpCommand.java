/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;

public class HelpCommand extends Command {
	
	// Constructor
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Create dialog box
		Dialog helpMenu = new Dialog("Here to Help!");
		helpMenu.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		// Help information for acceleration command
		TextArea accelerateHelp = new TextArea("Accelerate - Use the Accelerate button onscreen, the Accelerate option on "
									+ "the side menu, or the 'a' key on your keyboard");
		accelerateHelp.setUIID("AccelerateHelp");
		accelerateHelp.setEditable(false);
		
		// Help information for brake command
		TextArea brakeHelp = new TextArea("Brake - Use the Brake button onscreen or the 'b' key on your keyboard");
		brakeHelp.setUIID("BrakeHelp");
		brakeHelp.setEditable(false);
		
		// Help information for turn left command
		TextArea turnLeftHelp = new TextArea("Turn Left - Use the Left button onscreen or the 'l' key on your keyboard");
		turnLeftHelp.setUIID("TurnLeftHelp");
		turnLeftHelp.setEditable(false);
		
		// Help information for turn right command
		TextArea turnRightHelp = new TextArea("Turn Right - Use the Right button onscreen or the 'r' key on your keyboard");
		turnRightHelp.setUIID("TurnRightHelp");
		turnRightHelp.setEditable(false);
		
		// Help information for collide with NPC command
		TextArea collideNPCHelp = new TextArea("Collide with NPC - Use the Collide with NPC button onscreen");
		collideNPCHelp.setUIID("CollideNPCHelp");
		collideNPCHelp.setEditable(false);
		
		// Help information for collide with energy station command
		TextArea collideEnergyHelp = new TextArea("Collide with Energy Station - Use the Collide with Energy Station button onscreen");
		collideEnergyHelp.setUIID("CollideEnergyHelp");
		collideEnergyHelp.setEditable(false);
		
		// Help information for collide with drone command
		TextArea collideDroneHelp = new TextArea("Collide with Drone - Use the Collide with Drone button onscreen");
		collideDroneHelp.setUIID("CollideDroneHelp");
		collideDroneHelp.setEditable(false);
		
		// Help information for tick command
		TextArea tickHelp = new TextArea("Tick - Use the Tick button onscreen");
		tickHelp.setUIID("TickHelp");
		tickHelp.setEditable(false);
		
		// Help information for change strategies command
		TextArea changeStratHelp = new TextArea("Change Strategies - Use the Change Strategies command onscreen");
		changeStratHelp.setUIID("ChangeStratHelp");
		changeStratHelp.setEditable(false);
		
		// Help information for sound
		TextArea soundHelp = new TextArea("Sound - Check/Uncheck the box to turn sound ON and OFF");
		soundHelp.setUIID("SoundHelp");
		soundHelp.setEditable(false);
		
		// Help information for exit
		TextArea exitHelp = new TextArea("Exit - You can exit the game by clicking exit in the side menu");
		exitHelp.setUIID("ExitHelp");
		exitHelp.setEditable(false);
		
		// Add each piece of information to the dialog box
		helpMenu.add(accelerateHelp);
		helpMenu.add(brakeHelp);
		helpMenu.add(turnLeftHelp);
		helpMenu.add(turnRightHelp);
		helpMenu.add(collideNPCHelp);
		helpMenu.add(collideEnergyHelp);
		helpMenu.add(collideDroneHelp);
		helpMenu.add(tickHelp);
		helpMenu.add(changeStratHelp);
		helpMenu.add(soundHelp);
		helpMenu.add(exitHelp);
		
		// Create a command to exit the dialog box
		Command ok = new Command("OK");
		
		// Show the help menu
		Dialog.show(null, helpMenu, ok);
	}

}
