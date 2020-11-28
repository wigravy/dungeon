package com.wigravy.dungeon.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wigravy.dungeon.controllers.GameController;
import com.wigravy.dungeon.gameWorld.WorldRenderer;

public class GameScreen extends AbstractScreen {
    private GameController gameController;
    private WorldRenderer worldRenderer;

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        this.gameController = new GameController(batch);
        this.worldRenderer = new WorldRenderer(gameController, batch);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        worldRenderer.render();
    }
}
