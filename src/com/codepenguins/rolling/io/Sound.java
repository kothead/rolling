package com.codepenguins.rolling.io;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    
	public Sound(final String fileName) {
		
		Thread myThready = new Thread(new Runnable() {
            public void run() {
            	try {
	            	File soundFile = new File(fileName);
	    		    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
	    		    Clip clip = AudioSystem.getClip();
	    		    clip.open(ais);
	    		    clip.setFramePosition(0);
	    		    clip.start();
	    		    Thread.sleep(clip.getMicrosecondLength()/1000);
	    		    clip.stop();
	    		    clip.close();
	            } catch(IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException exc) {
	    		    exc.printStackTrace();
	    		}
            }
        });
        myThready.start();
        
	}
}