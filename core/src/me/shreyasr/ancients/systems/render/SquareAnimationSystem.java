package me.shreyasr.ancients.systems.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import me.shreyasr.ancients.components.HitboxComponent;
import me.shreyasr.ancients.components.KnockbackComponent;
import me.shreyasr.ancients.components.PositionComponent;
import me.shreyasr.ancients.components.SquareAnimationComponent;
import me.shreyasr.ancients.components.SquareDirectionComponent;
import me.shreyasr.ancients.components.TextureTransformComponent;
import me.shreyasr.ancients.components.VelocityComponent;

public class SquareAnimationSystem extends IteratingSystem {

    public SquareAnimationSystem(int priority) {
        super(
                Family.all(SquareAnimationComponent.class,
                           TextureTransformComponent.class)
                      .get(),
                priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SquareAnimationComponent anim = SquareAnimationComponent.MAPPER.get(entity);
        TextureTransformComponent transform = TextureTransformComponent.MAPPER.get(entity);
        SquareDirectionComponent direction = SquareDirectionComponent.MAPPER.get(entity);
        VelocityComponent vel = VelocityComponent.MAPPER.get(entity);
        KnockbackComponent knockback = KnockbackComponent.MAPPER.get(entity);

        if (knockback != null && knockback.isActive()) {
            vel = null;
        }

        if (vel == null || (vel.dx == 0 && vel.dy == 0)) {
            anim.timeSinceLastFrame = 0;
            anim.currentFrame = 0;
        } else {
            anim.timeSinceLastFrame += deltaTime;
            if (anim.timeSinceLastFrame >= anim.frameTime) {
                anim.timeSinceLastFrame = 0;
                anim.currentFrame++;
                if (anim.currentFrame >= anim.length) {
                    anim.currentFrame = 0;
                }
            }
        }

        int dirIndex = direction == null ? 0 : direction.dir.index;

        transform.screenWidth = anim.frameWidth * 4;
        transform.screenHeight = anim.frameHeight * 4;
        transform.originX = transform.screenWidth/2;
        transform.originY = transform.screenHeight/2;
        transform.srcX = anim.currentFrame * anim.frameWidth;
        transform.srcY = dirIndex * anim.frameHeight;
        transform.srcWidth = anim.frameWidth;
        transform.srcHeight = anim.frameHeight;
        transform.rotation = 0;

        PositionComponent pos = PositionComponent.MAPPER.get(entity);
        HitboxComponent hitbox = HitboxComponent.MAPPER.get(entity);
        hitbox.x = pos.x - transform.originX;
        hitbox.y = pos.y - transform.originY;
        hitbox.w = anim.frameWidth*4;
        hitbox.h = anim.frameHeight*4;
    }
}
