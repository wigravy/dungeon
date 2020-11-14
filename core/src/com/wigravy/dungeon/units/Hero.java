package com.wigravy.dungeon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.wigravy.dungeon.controllers.ProjectileController;
import com.wigravy.dungeon.utils.FiringMode;

public class Hero {
    private ProjectileController projectileController;
    private Vector2 position;
    private TextureRegion texture;
    private float speed;
    private FiringMode firingMode;
    private Vector2 lastPosition;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.position = new Vector2(100, 100);
        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;
        this.speed = 50.0f;
        this.firingMode = FiringMode.SINGLE_SHOOT;
        this.lastPosition = new Vector2();
    }

    public void update(float dt) {
        move(dt);
        checkPosition();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            switchFiringMode();
        }
    }

    public void switchFiringMode() {
        if (firingMode == FiringMode.SINGLE_SHOOT) {
            firingMode = FiringMode.DOUBLE_SHOOT;
        } else {
            firingMode = FiringMode.SINGLE_SHOOT;
        }
    }

    public void fire() {
        if (firingMode == FiringMode.SINGLE_SHOOT) {
            projectileController.activate(position.x, position.y, lastPosition.x, lastPosition.y);
        } else {
            if (lastPosition.y == 0) {
                projectileController.activate(position.x, position.y, lastPosition.x, lastPosition.y + 25);
                projectileController.activate(position.x, position.y, lastPosition.x, lastPosition.y - 25);
            } else {
                projectileController.activate(position.x, position.y, lastPosition.x + 25, lastPosition.y);
                projectileController.activate(position.x, position.y, lastPosition.x - 25, lastPosition.y);
            }
        }
    }

    public void checkPosition() {
        if (position.x < 0.0f) {
            position.x = 0.0f;
        }
        if (position.x > Gdx.graphics.getWidth()) {
            position.x = Gdx.graphics.getWidth();
        }
        if (position.y < 0.0f) {
            position.y = 0.0f;
        }
        if (position.y > Gdx.graphics.getHeight()) {
            position.y = Gdx.graphics.getHeight();
        }
    }

    public void move(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * dt;
            lastPosition.set(200, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * dt;
            lastPosition.set(-200, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * dt;
            lastPosition.set(0, 200);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * dt;
            lastPosition.set(0, -200);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 20, position.y - 20);
    }
}
