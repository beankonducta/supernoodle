package com.patrick.game.util;

import com.patrick.game.entities.Entity;

public class Misc {

    public static boolean PLAYER_BOWL_MATCH(Entity player, Entity bowl) {
        if(bowl == null || player == null) return false;
        return (player.getId() == 1 && bowl.getId() == -3) || (player.getId() == 2 && bowl.getId() == -2);
    }

    public static boolean PLAYER_BOWL_MATCH_ID(int playerId, int bowlId) {
        if(bowlId == -1 || playerId == -1) return false;
        return (playerId == 1 && bowlId == -3) || (playerId == 2 && bowlId == -2);
    }
}
