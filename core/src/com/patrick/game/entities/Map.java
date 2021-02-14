package com.patrick.game.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Map {

    protected List<Floor> floors;
    protected List<Cloud> clouds;
    protected List<Ingredient> ingredients;
    protected List<Player> players;
    protected List<Bowl> bowls;

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Bowl> getBowls() {
        return bowls;
    }

    public void setBowls(List<Bowl> bowls) {
        this.bowls = bowls;
    }

    public Map() {
        this.floors = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.players = new ArrayList<>();
        this.bowls = new ArrayList<>();
    }

    public List<Entity> entities() {
        List<Entity> e = new ArrayList<>();
        e.addAll(this.floors);
        e.addAll(this.clouds);
        e.addAll(this.ingredients);
        e.addAll(this.players);
        e.addAll(this.bowls);
        return e;
    }
}