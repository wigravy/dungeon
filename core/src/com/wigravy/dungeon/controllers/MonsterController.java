package com.wigravy.dungeon.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wigravy.dungeon.units.Monster;

public class MonsterController {
    private static final int MAX_MONSTERS = 100;

    private GameController gameController;
    private Monster[] monsters;

    public Monster[] getMonsters() {
        return monsters;
    }

    public MonsterController(TextureAtlas atlas, GameController gameController) {
        this.gameController = gameController;
        this.monsters = new Monster[MAX_MONSTERS];
        for (int i = 0; i < monsters.length; i++) {
            monsters[i] = new Monster(atlas, gameController);
        }
    }

    public void activate(int cellX, int cellY) {
        for (Monster m : monsters) {
            if (!m.isActive()) {
                m.activate(cellX, cellY);
                return;
            }
        }
    }

    public void update(float dt) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                m.update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                m.render(batch);
            }
        }
    }

    public Monster getMonsterInCell(int cellX, int cellY) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                if (m.getCellX() == cellX && m.getCellY() == cellY) {
                    return m;
                }
            }
        }
        return null;
    }
}
