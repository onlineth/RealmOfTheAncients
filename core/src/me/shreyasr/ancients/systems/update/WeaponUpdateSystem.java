package me.shreyasr.ancients.systems.update;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;

import me.shreyasr.ancients.components.PositionComponent;
import me.shreyasr.ancients.components.TextureTransformComponent;
import me.shreyasr.ancients.components.weapon.OwnerUUIDComponent;
import me.shreyasr.ancients.components.weapon.WeaponAnimationComponent;

public class WeaponUpdateSystem extends IteratingSystem {

    private final PooledEngine engine;

    public WeaponUpdateSystem(int priority, PooledEngine engine) {
        super(
                Family.all(WeaponAnimationComponent.class,
                           WeaponAnimationComponent.class,
                           TextureTransformComponent.class)
                        .get(),
                priority);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureTransformComponent transform = TextureTransformComponent.MAPPER.get(entity);
        WeaponAnimationComponent anim = WeaponAnimationComponent.MAPPER.get(entity);
        PositionComponent pos = PositionComponent.MAPPER.get(entity);
        OwnerUUIDComponent ownerId = OwnerUUIDComponent.MAPPER.get(entity);

        Entity owner = engine.getEntity(ownerId.engineId);
        PositionComponent ownerPos = PositionComponent.MAPPER.get(owner);

        pos.x = ownerPos.x;
        pos.y = ownerPos.y;

        anim.timeSinceAnimStart += deltaTime;

        if (anim.isDone()) {
            engine.removeEntity(entity);
        } else {
            transform.screenWidth = anim.frameSize * 4;
            transform.screenHeight = anim.frameSize * 4;
            transform.originX = transform.screenWidth / 2;
            transform.originY = transform.screenHeight / 2;
            transform.srcX = anim.getCurrentFrame() * anim.frameSize;
            transform.srcY = 0;
            transform.srcWidth = anim.frameSize;
            transform.srcHeight = anim.frameSize;
            transform.rotation = 0;
        }
    }
}