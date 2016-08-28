package com.magicka.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.magicka.Magicka;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new LwjglApplication(new Magicka(), "Magicka", Magicka.SCREEN_WIDTH, Magicka.SCREEN_HEIGHT);
	}
}
