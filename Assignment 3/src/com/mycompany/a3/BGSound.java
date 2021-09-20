/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {
	private Media m;
	
	// Constructor
	public BGSound(String fileName) {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			m = MediaManager.createMedia(is, "audio/wav", this);
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Pause the sound
	public void pause() {
		m.pause();
	}
	
	// Continue playing the sound where left off
	public void play() {
		m.play();
	}
	
	@Override
	public void run() {
		m.setTime(0);		// Start playing from beginning of sound file
		m.play();
		
	}

}
