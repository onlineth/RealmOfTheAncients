package me.shreyasr.ancients.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class SquareDirectionComponent implements Component, Pool.Poolable {

    public static ComponentMapper<SquareDirectionComponent> MAPPER
            = ComponentMapper.getFor(SquareDirectionComponent.class);

    public static SquareDirectionComponent create(PooledEngine engine) {
        return engine.createComponent(SquareDirectionComponent.class);
    }

    public enum Direction {

        RIGHT(0), UP(1), LEFT(2), DOWN(3);

        public final int index;

        Direction(int index) {
            this.index = index;
        }

        public int getX() {
            return -(index-1) % 2;
        }

        public int getY() {
            return -(index-2) % 2;
        }

        public static Direction getFromPos(float dx, float dy) {
            if (Math.abs(dx) >= Math.abs(dy)) {
                if (dx >= 0) return RIGHT;
                else return LEFT;
            } else {
                if (dy >= 0) return UP;
                else return DOWN;
            }
        }
    }



    public Direction dir;

    public SquareDirectionComponent() {
        reset();
    }

    @Override
    public void reset() {
        dir = Direction.DOWN;
    }
}