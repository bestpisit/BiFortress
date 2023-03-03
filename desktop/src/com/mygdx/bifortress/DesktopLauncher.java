package com.mygdx.bifortress;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(120);
		config.setTitle("BiFortress");
		config.setWindowSizeLimits(1440,900,1920,1080);
		config.setWindowIcon("ui/BiFortress/BiFortress128x128.png");
		new Lwjgl3Application(new BiFortress(), config);
	}
}
