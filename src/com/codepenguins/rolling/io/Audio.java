package com.codepenguins.rolling.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import static org.lwjgl.openal.AL10.*;
import org.lwjgl.util.WaveData;

public class Audio {
	public Audio() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadAudio(String fileName) throws FileNotFoundException {
		WaveData data = WaveData.create(new FileInputStream(fileName));
		int buffer = alGenBuffers();
        alBufferData(buffer, data.format, data.data, data.samplerate);
        data.dispose();
        int source = alGenSources();
        alSourcei(source, AL_BUFFER, buffer);
        alSourcePlay(source);
		alDeleteBuffers(buffer);
		AL.destroy();
	}
}
