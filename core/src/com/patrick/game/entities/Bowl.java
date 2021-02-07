package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Bowl extends StaticEntity {

    protected List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        if(this.ingredients != null)
        this.ingredients.add(ingredient);
        // update sprite here
    }

    public Entity removeIngredient(int index) {
        if(this.ingredients != null)
            if(index < this.ingredients.size()) {
                Entity e = this.ingredients.get(index);
                this.ingredients.remove(e);
                return e;
            }
        return null;
    }

    public Entity removeLastIngredient() {
        return this.removeIngredient(this.getIngredientCount() - 1);
    }

    public int getIngredientCount() {
        if(this.ingredients != null)
        return this.ingredients.size();
        return 0;
    }
    public Bowl(Vector2 position, Texture texture, int id) {
        super(position, texture);
        this.ingredients = new ArrayList<Ingredient>();
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        this.id = id;
    }
}
