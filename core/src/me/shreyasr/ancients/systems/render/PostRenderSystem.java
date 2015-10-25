package me.shreyasr.ancients.systems.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PostRenderSystem extends EntitySystem {

    private final ShapeRenderer shape;

    public PostRenderSystem(int priority, ShapeRenderer shape) {
        super(priority);
        this.shape = shape;
    }

    public void update(float deltaTime) {
        shape.end();
    }
}
