package com.patrick.game.util;

import com.patrick.game.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class MapLoader {

    public MapLoader() {

    }

    public List<Entity> loadMap(BufferedImage mapImage) {
        for(int i = 0; i < mapImage.getWidth(); i ++) {
            for(int j = 0; j < mapImage.getHeight(); j ++) {
                Color c = new Color(mapImage.getRGB(i, j));
                /*
                if color = whatever, list.add new wall
                if color = whatever, list.add ingredient
                if color = whatever, list.add player
                if color = whatever, list.add bowl
                 */
            }
        }
    }

}
