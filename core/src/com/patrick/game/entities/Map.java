package com.patrick.game.entities;

import java.util.ArrayList;
import java.util.List;

public class Map {

    protected List<Floor> floors;
    protected List<Cloud> clouds;
    protected List<Ingredient> ingredients;
    protected List<Player> players;
    protected List<Bowl> bowls;
    protected List<Effect> effects;
    protected List<Particle> particles;

    public List<Floor> getFloors() {
        return this.floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Cloud> getClouds() {
        return this.clouds;
    }

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Bowl> getBowls() {
        return this.bowls;
    }

    public void setBowls(List<Bowl> bowls) {
        this.bowls = bowls;
    }

    public List<Effect> getEffects() {
        return this.effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public List<Particle> getParticles() {
        return this.particles;
    }

    public void setParticles(List<Particle> particles) {
        this.particles = particles;
    }

    public void addEffect(Effect e) {
        this.effects.add(e);
    }

    public void removeEffect(Effect e) {
        this.effects.remove(e);
    }

    public void addParticle(Particle p) {
        this.particles.add(p);
    }

    public void removeParticle(Particle p) {
        this.particles.remove(p);
    }

    public Player playerOne() {
        if(this.players.size() >= 1) return this.players.get(0);
        return null;
    }

    public Player playerTwo() {
        if(this.players.size() >= 2) return this.players.get(1);
        return null;
    }

    public Bowl bowlOne() {
        if(this.bowls.size() >= 1) return this.bowls.get(0);
        return null;
    }

    public Bowl bowlTwo() {
        if(this.bowls.size() >= 2) return this.bowls.get(1);
        return null;
    }

    public Effect findEffectByParent(Entity e) {
        for (Effect f : this.effects) {
            if (f.getParentId() == e.getId()) return f;
        }
        return null;
    }

    public Map() {
        this.floors = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.players = new ArrayList<>();
        this.bowls = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.particles = new ArrayList<>();
    }

    public List<Entity> entities() {
        List<Entity> e = new ArrayList<>();
        e.addAll(this.floors);
        e.addAll(this.ingredients);
        e.addAll(this.bowls);
        e.addAll(this.clouds);
        e.addAll(this.players);
        e.addAll(this.effects);
        e.addAll(this.particles);
        return e;
    }

    public List<Entity> entitiesWithoutClouds() {
        List<Entity> e = new ArrayList<>();
        e.addAll(this.floors);
        e.addAll(this.ingredients);
        e.addAll(this.bowls);
        e.addAll(this.players);
        e.addAll(this.effects);
        e.addAll(this.particles);
        return e;
    }

    public List<Entity> playersAndIngredients() {
        List<Entity> e = new ArrayList<>();
        e.addAll(this.ingredients);
        e.addAll(this.players);
        return e;
    }
}