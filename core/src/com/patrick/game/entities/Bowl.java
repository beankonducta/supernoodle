package com.patrick.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class Bowl extends StaticEntity {

    protected List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        if(this.ingredients == null) return;
            this.ingredients.add(ingredient);
            this.updateTexture();
    }

    public Entity removeIngredient(int index) {
        if(this.ingredients != null) {
            if (index < this.ingredients.size()) {
                final Entity e = this.ingredients.get(index);
                this.ingredients.remove(e);
                this.updateTexture();
                return e;
            }
        }
        return null;
    }

    public Entity removeLastIngredient() {
        if(this.getIngredientCount() > 0)
        return this.removeIngredient(this.getIngredientCount() - 1);
        return null;
    }

    public int getIngredientCount() {
        if(this.ingredients == null) return 0;
        return this.ingredients.size();
    }

    public void updateTexture() {
        this.texture = Resources.BOWL(this.ingredients.size());
    }

    public Bowl(Vector2 position, int id) {
        super(position, null);
        this.ingredients = new ArrayList<Ingredient>();
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.id = id;
        this.updateTexture();
    }
}
