/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	
	// Constructor
	public Sound(String fileName) {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			m = MediaManager.createMedia(is,  "audio/wav");
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		// Start playing the sound from the beginning of sound file
		m.setTime(0);
		m.play();
	}
}
