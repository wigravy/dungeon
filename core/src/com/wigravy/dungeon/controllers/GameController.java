package com.wigravy.dungeon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wigravy.dungeon.utils.GameMap;

public class GameController {
    private ProjectileController projectileController;
    private UnitController unitController;
    private GameMap gameMap;

    private int cursorX, cursorY;

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public ProjectileController getProjectileController() {
        return projectileController;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public UnitController getUnitController() {
        return unitController;
    }

    public GameController(TextureAtlas atlas) {
        this.gameMap = new GameMap(atlas);
        this.unitController = new UnitController(this, atlas);
        this.projectileController = new ProjectileController(atlas);
        this.unitController.init();
    }

    public void update(float dt) {
        cursorX = (Gdx.input.getX() / GameMap.CELL_SIZE);
        cursorY = ((720 - Gdx.input.getY()) / GameMap.CELL_SIZE);
        projectileController.update(dt);
        unitController.update(dt);
    }
}
