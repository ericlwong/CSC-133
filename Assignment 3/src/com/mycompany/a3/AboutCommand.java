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

public class AboutCommand extends Command {
	
	// Constructor
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("About command is invoked...");
		Dialog aboutBox = new Dialog("About");
		aboutBox.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		// Create a text area for my name
		TextArea myName = new TextArea("Eric Wong");
		myName.setUIID("MyName");
		myName.setEditable(false);	// Make it only readable
		
		// Create a text area for the course name
		TextArea courseName = new TextArea("CSC 133");
		courseName.setUIID("CourseName");
		courseName.setEditable(false);		// Make it only readable
		
		// Create a text area for the assignment number
		TextArea assignmentNum = new TextArea("Assignment 2");
		assignmentNum.setUIID("AssignmentNumber");
		assignmentNum.setEditable(false);
		
		// Add to dialog box
		aboutBox.add(myName);
		aboutBox.add(courseName);
		aboutBox.add(assignmentNum);
		
		// Create a command to exit the dialog box
		Command ok = new Command("OK");
		
		// Show the information
		Dialog.show(null, aboutBox, ok);
	}

}
