package com.squareroot.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    private AudioClip clip;

    Sound(String path) {
	clip = Applet.newAudioClip(Sound.class.getResource(path));

    }

    public void play() {
	new Thread() {
	    @Override
	    public void run() {		
		clip.play();
	    }
	}.start();

    }

    public void loop() {
	new Thread() {
	    @Override
	    public void run() {
		clip.loop();
	    }
	}.start();

    }
}
