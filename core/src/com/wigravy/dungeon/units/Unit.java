package com.wigravy.dungeon.units;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.wigravy.dungeon.GameMap;
import com.wigravy.dungeon.controllers.GameController;

public abstract class Unit {
    GameController gameController;
    TextureRegion texture;
    TextureRegion textureHp;
    int currentHp, maxHp;
    int cellX, cellY;
    Vector2 temp;
    int actionPoints, maxActionPoints;
    BitmapFont font;

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public Unit(GameController gameController, int cellX, int cellY, int maxHp) {
        this.gameController = gameController;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.cellX = cellX;
        this.cellY = cellY;
        this.temp = new Vector2(0, 0);
        this.font = new BitmapFont();
    }

    public boolean takeDamage(int amount) {
        currentHp -= amount;
        return currentHp <= 0;
    }

    public abstract void update(float dt);

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * GameMap.CELL_SIZE, cellY * GameMap.CELL_SIZE);
        batch.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, cellX * GameMap.CELL_SIZE + 1, cellY * GameMap.CELL_SIZE + 51, 58, 10);
        batch.setColor(0.7f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, cellX * GameMap.CELL_SIZE + 2, cellY * GameMap.CELL_SIZE + 52, 56, 8);
        batch.setColor(0.0f, 1.0f, 0.0f, 1.0f);
        batch.draw(textureHp, cellX * GameMap.CELL_SIZE + 2, cellY * GameMap.CELL_SIZE + 52, (float) currentHp / maxHp * 56, 8);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
