/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {

	// Each required label in the ScoreView
	private Label clockLabel = new Label("Time: 0 ");
	private Label livesLabel = new Label("Lives Remaining: 0 ");
	private Label lastBaseLabel = new Label("Last Base Reached: 0 ");
	private Label energyLabel = new Label("Energy Level: 0 ");
	private Label damageLabel = new Label("Damage Sustained: 0 ");
	private Label soundStatus = new Label("Sound: OFF ");
	
	// Constructor
	public ScoreView() {
		this.setLayout(new FlowLayout(Component.CENTER));
		
		// Set the styling for the labels
		clockLabel.getAllStyles().setPadding(RIGHT, 5);
		clockLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesLabel.getAllStyles().setPadding(RIGHT, 5);
		livesLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastBaseLabel.getAllStyles().setPadding(RIGHT, 5);
		lastBaseLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		energyLabel.getAllStyles().setPadding(RIGHT, 5);
		energyLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		damageLabel.getAllStyles().setPadding(RIGHT, 5);
		damageLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundStatus.getAllStyles().setPadding(RIGHT, 5);
		soundStatus.getAllStyles().setFgColor(ColorUtil.BLUE);

		// Add the labels to the ScoreView
		this.addComponent(clockLabel);
		this.addComponent(livesLabel);
		this.addComponent(lastBaseLabel);
		this.addComponent(energyLabel);
		this.addComponent(damageLabel);
		this.addComponent(soundStatus);
	}
	
	@Override
	public void update(Observable observable, Object data) {		// Update the ScoreView each time there is a modification
		clockLabel.setText("Time: " + ((GameWorld)observable).getClockTimer());
		livesLabel.setText("Lives Remaining: " + ((GameWorld)observable).getLivesRemaining());
		lastBaseLabel.setText("Last Base Reached: " + ((GameWorld)observable).getLastBaseReached());
		energyLabel.setText("Energy Level: " + ((GameWorld)observable).getEnergyLevel());
		damageLabel.setText("Damage Sustained: " + ((GameWorld)observable).getDamageLevel());
		
		if ((boolean)((GameWorld)observable).getSound() == false) {
			soundStatus.setText("Sound: OFF");
		} else {
			soundStatus.setText("Sound: ON");
		}
		this.repaint();
	}

}
