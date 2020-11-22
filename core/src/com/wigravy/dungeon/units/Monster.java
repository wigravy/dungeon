package com.wigravy.dungeon.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.wigravy.dungeon.controllers.GameController;

public class Monster extends Unit {
    private float aiBrainsImplsTime;
    private Unit target;
    private float maxAggroRadius;

    public Monster(TextureAtlas atlas, GameController gc) {
        super(gc, 5, 2, 10);
        this.texture = atlas.findRegion("monster");
        this.textureHp = atlas.findRegion("hp");
        this.hp = -1;
        this.maxAggroRadius = 5.0f;
    }

    public void activate(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.targetX = cellX;
        this.targetY = cellY;
        this.hpMax = 10;
        this.hp = hpMax;
        this.target = gc.getUnitController().getHero();
    }

    public void update(float dt) {
        super.update(dt);
        if (canIMakeAction()) {
            if (isStayStill()) {
                aiBrainsImplsTime += dt;
            }
            if (aiBrainsImplsTime > 0.4f) {
                aiBrainsImplsTime = 0.0f;
                if (canIAttackThisTarget(target)) {
                    attack(target);
                } else {
                    tryToMove();
                }
            }
        }
    }

    public void tryToMove() {
        int bestX = -1, bestY = -1;
        float bestDst = 10000;
        for (int i = cellX - 1; i <= cellX + 1; i++) {
            for (int j = cellY - 1; j <= cellY + 1; j++) {
                if (Math.abs(cellX - i) + Math.abs(cellY - j) == 1 && gc.getGameMap().isCellPassable(i, j) && gc.getUnitController().isCellFree(i, j)) {
                    float dst = (float) Math.sqrt((i - target.getCellX()) * (i - target.getCellX()) + (j - target.getCellY()) * (j - target.getCellY()));
                    if (dst <= maxAggroRadius) {
                        if (dst < bestDst) {
                            bestDst = dst;
                            bestX = i;
                            bestY = j;
                        }
                    } else {
                        do {
                            bestX = cellX + MathUtils.random(-1, 1);
                            bestY = cellY + MathUtils.random(-1, 1);
                        } while (Math.abs((bestX + bestY) - (cellX + cellY)) != 1);
                        goTo(bestX, bestY);
                        return;
                    }
                }
            }
        }

        goTo(bestX, bestY);
    }
}
