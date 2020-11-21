package com.wigravy.dungeon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wigravy.dungeon.GameMap;
import com.wigravy.dungeon.controllers.GameController;

public class Hero extends Unit {
    private float movementTime;
    private float movementMaxTime;
    private int targetX, targetY;
    private int experience;

    public Hero(TextureAtlas atlas, GameController gameController) {
        super(gameController, 1, 1, 10);
        this.texture = atlas.findRegion("knight");
        this.textureHp = atlas.findRegion("hp");
        this.movementMaxTime = 0.2f;
        this.targetX = cellX;
        this.targetY = cellY;
        this.experience = 0;
        this.maxActionPoints = 5;
        this.actionPoints = maxActionPoints;
    }

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public void resetMovePoints() {
        this.actionPoints = maxActionPoints;
    }

    @Override
    public void update(float dt) {
        checkMovement(dt);
    }

    public boolean isStayStill() {
        return cellY == targetY && cellX == targetX;
    }

    public void checkMovement(float dt) {
        if (actionPoints == 0) {
            resetMovePoints();
        }

        if (Gdx.input.justTouched() && isStayStill()) {
            if (Math.abs(gameController.getCursorX() - cellX) + Math.abs(gameController.getCursorY() - cellY) == 1) {
                targetX = gameController.getCursorX();
                targetY = gameController.getCursorY();
            }
        }

        Monster monster = gameController.getMonsterController().getMonsterInCell(targetX, targetY);

        if (monster != null) {
            targetX = cellX;
            targetY = cellY;
            if (monster.takeDamage(1)) {
                addExperience(1);
            }
            actionPoints--;
        }

        if (!gameController.getGameMap().isCellPassable(targetX, targetY)) {
            targetX = cellX;
            targetY = cellY;
        }

        if (!isStayStill()) {
            movementTime += dt;
            if (movementTime > movementMaxTime) {
                movementTime = 0;
                cellX = targetX;
                cellY = targetY;
                actionPoints--;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        float px = cellX * GameMap.CELL_SIZE;
        float py = cellY * GameMap.CELL_SIZE;
        if (!isStayStill()) {
            px = cellX * GameMap.CELL_SIZE + (targetX - cellX) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
            py = cellY * GameMap.CELL_SIZE + (targetY - cellY) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
        }
        font.draw(batch, String.format("AP: %d / %d", actionPoints, maxActionPoints), px, py);
        batch.draw(texture, px, py);
        batch.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, px + 1, py + 51, 58, 10);
        batch.setColor(0.7f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, px + 2, py + 52, 56, 8);
        batch.setColor(0.0f, 1.0f, 0.0f, 1.0f);
        batch.draw(textureHp, px + 2, py + 52, (float) currentHp / maxHp * 56, 8);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
