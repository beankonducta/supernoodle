package com.patrick.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class Bowl extends StaticEntity {

    protected List<Ingredient> ingredients;

    protected Rectangle ingredientCollider;

    public Rectangle getIngredientCollider() {
        return this.ingredientCollider;
    }

    public Bowl(Vector2 position, int id) {
        super(position, null);
        this.ingredients = new ArrayList<Ingredient>();
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.ingredientCollider = new Rectangle(position.x - Settings.TILE_SIZE, position.y, Settings.TILE_SIZE * 3, Settings.TILE_SIZE);
        this.id = id;
        this.updateTexture();
    }

    /**
     * Adds an ingredient to the bowl.
     *
     * @param ingredient
     */
    public void addIngredient(Ingredient ingredient) {
        if(this.ingredients == null) return;
            this.ingredients.add(ingredient);
            this.updateTexture();
    }

    /**
     * Removes an ingredient at specified index.
     *
     * @param index
     * @return
     */
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

    /**
     * Removes the most recently added ingredient from the bowl.
     *
     * @return
     */
    public Entity removeLastIngredient() {
        if(this.getIngredientCount() > 0)
        return this.removeIngredient(this.getIngredientCount() - 1);
        return null;
    }

    /**
     * Returns the total count of ingredients in the bowl.
     *
     * @return
     */
    public int getIngredientCount() {
        if(this.ingredients == null) return 0;
        return this.ingredients.size();
    }

    /**
     * Updates the texture based on the amount of ingredients in the bowl.
     *
     */
    public void updateTexture() {
        this.texture = Resources.BOWL(this.ingredients.size());
    }
}
