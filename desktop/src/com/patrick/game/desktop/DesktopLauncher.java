package com.patrick.game.desktop;

import com.badlogic.gdx.backends.jogamp.JoglNewtApplication;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.jogamp.JoglNewtApplicationConfiguration;
import com.patrick.game.SuperNoodle;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new SuperNoodle(), config);
//        JoglNewtApplicationConfiguration config = new JoglNewtApplicationConfiguration();
//        config.width = 100;
//        config.height = 100;
//        new JoglNewtApplication(new SuperNoodle(), config);
    }
}