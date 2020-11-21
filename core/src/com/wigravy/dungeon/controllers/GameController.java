package com.wigravy.dungeon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wigravy.dungeon.GameMap;
import com.wigravy.dungeon.units.Hero;

public class GameController {
    ProjectileController projectileController;
    private Hero hero;
    private MonsterController monsterController;
    private GameMap gameMap;

    private int cursorX, cursorY;

    public ProjectileController getProjectileController() {
        return projectileController;
    }

    public Hero getHero() {
        return hero;
    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public GameController(TextureAtlas atlas) {
        this.projectileController = new ProjectileController(atlas);
        this.hero = new Hero(atlas, this);
        this.monsterController = new MonsterController(atlas, this);
        this.gameMap = new GameMap(atlas);
        this.monsterController.activate(5, 5);
        this.monsterController.activate(8, 8);
    }

    public void update(float dt) {
        cursorX = Gdx.input.getX() / GameMap.CELL_SIZE;
        cursorY = (720 - Gdx.input.getY()) / GameMap.CELL_SIZE;
        projectileController.update(dt);
        hero.update(dt);
        monsterController.update(dt);
    }
}
