package me.shreyasr.ancients.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class SquareAnimationComponent implements Component, Pool.Poolable {

    public static ComponentMapper<SquareAnimationComponent> MAPPER
            = ComponentMapper.getFor(SquareAnimationComponent.class);

    public static SquareAnimationComponent create(PooledEngine engine, int length,
                                                  int frameWidth, int frameHeight, int frameTime) {
        SquareAnimationComponent anim = engine.createComponent(SquareAnimationComponent.class);
        anim.length = length;
        anim.frameWidth = frameWidth;
        anim.frameHeight = frameHeight;
        anim.frameTime = frameTime;
        return anim;
    }

    public int length; // number of frames in an animation
    public int frameWidth;  // width of a frame, in pixels
    public int frameHeight; // height of a frame, in pixels

    public int frameTime; // milliseconds per frame
    public int timeSinceLastFrame; // milliseconds since last frame

    public int currentFrame; // current frame

    public SquareAnimationComponent() {
        reset();
    }

    @Override
    public void reset() {
        length = 0;
        frameWidth = 0;
        frameHeight = 0;
        frameTime = 0;
        timeSinceLastFrame = 0;
        currentFrame = 0;
    }
}
