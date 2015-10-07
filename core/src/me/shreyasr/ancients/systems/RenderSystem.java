package me.shreyasr.ancients.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.shreyasr.ancients.components.PositionComponent;
import me.shreyasr.ancients.components.TextureComponent;
import me.shreyasr.ancients.components.TextureTransformComponent;

public class RenderSystem extends IteratingSystem {

    private final SpriteBatch batch;

    public RenderSystem(int priority, SpriteBatch batch) {
        super(
                Family.all(PositionComponent.class,
                           TextureComponent.class,
                           TextureTransformComponent.class)
                      .get(),
                priority);
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = PositionComponent.MAPPER.get(entity);
        TextureTransformComponent ttc = TextureTransformComponent.MAPPER.get(entity);

        Texture texture = TextureComponent.MAPPER.get(entity).texture;

        batch.draw(texture, pos.x, pos.y, ttc.originX, ttc.originY, ttc.screenWidth, ttc.screenHeight,
                1, 1, ttc.rotation, ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight, false, false);
    }
}
