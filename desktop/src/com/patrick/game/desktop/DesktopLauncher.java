package com.patrick.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.patrick.game.SuperNoodle;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new SuperNoodle(), config);
//        JoglNewtApplicationConfiguration config = new JoglNewtApplicationConfiguration();
//        config.width = 100;
//        config.height = 100;
//        new JoglNewtApplication(new SuperNoodle(), config);
    }
}