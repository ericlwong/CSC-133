/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.Border;

public class PausePlayCommand extends Command {
	private Game g;
	private Button pausePlayButton;
	private boolean paused;
	
	// Constructor
	public PausePlayCommand(Game g, Button pausePlayButton) {
		super("Pause");
		this.g = g;
		this.pausePlayButton = pausePlayButton;
		paused = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If game is not paused, the pausePlayButton should display "Pause"
		if (paused) {
			this.pausePlayButton.setText("Pause"); 
			
			pausePlayButton.getUnselectedStyle().setBgTransparency(255);
			pausePlayButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
			pausePlayButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
			pausePlayButton.getSelectedStyle().setBgTransparency(255);
			pausePlayButton.getSelectedStyle().setBgColor(ColorUtil.WHITE);
			pausePlayButton.getSelectedStyle().setFgColor(ColorUtil.BLUE);
			pausePlayButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
			
			g.play();
		
		// If the game is paused, the pausePlayButton should display "Play"
		} else if (!paused) {
			this.pausePlayButton.setText("Play");
			
			pausePlayButton.getUnselectedStyle().setBgTransparency(255);
			pausePlayButton.getUnselectedStyle().setBgColor(ColorUtil.WHITE);
			pausePlayButton.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
			pausePlayButton.getSelectedStyle().setBgTransparency(255);
			pausePlayButton.getSelectedStyle().setBgColor(ColorUtil.WHITE);
			pausePlayButton.getSelectedStyle().setFgColor(ColorUtil.BLUE);
			pausePlayButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLUE));
			
			g.pause();
		}
		
		// Set boolean value paused to be the either TRUE or FALSE next depending on what it was previously
		paused = !paused;
	}

}
