package com.codepenguins.rolling.io;

import com.codepenguins.rolling.Game;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    
	private boolean isPlaying = true;
	
	public Sound(final String fileName) {
		
		Thread myThready = new Thread(new Runnable() {
			private static final int checkStateTime = 200;
            public void run() {
            	try {
	            	File soundFile = new File(fileName);
	    		    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
	    		    Clip clip = AudioSystem.getClip();
	    		    clip.open(ais);
	    		    clip.setFramePosition(0);
	    		    clip.start();
	    		    long trackLenght = clip.getMicrosecondLength();
	    		    for (long time = 0; time < trackLenght; time += checkStateTime) {
	    		    	if (!Sound.this.isPlaying || !Game.isRunning()) break;
	    		    	Thread.sleep(checkStateTime);
	    		    }
	    		    clip.stop();
	    		    clip.close();
	            } catch(IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException exc) {
	    		    exc.printStackTrace();
	    		}
            }
        });
        myThready.start();
        
	}
	
	public void stopPlaying() {
		isPlaying = false;
	}
}