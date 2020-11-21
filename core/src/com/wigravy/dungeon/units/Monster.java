package com.wigravy.dungeon.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wigravy.dungeon.controllers.GameController;

public class Monster extends Unit{
    private float counterAttackChance;
    public boolean isActive() {
        return currentHp > 0;
    }


    @Override
    public void update(float dt) {
    }

    public Monster(TextureAtlas atlas, GameController gameController) {
        super(gameController, 5, 2, 10);
        this.texture = atlas.findRegion("monster");
        this.textureHp = atlas.findRegion("hp");
        this.currentHp = -1;
        this.counterAttackChance = 0.0f;
    }

    public void activate(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.maxHp = 10;
        this.currentHp = maxHp;
        this.counterAttackChance = 0.25f;
    }
}
