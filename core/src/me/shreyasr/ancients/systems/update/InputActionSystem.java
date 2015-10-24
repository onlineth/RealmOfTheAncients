package me.shreyasr.ancients.systems.update;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.esotericsoftware.kryonet.Client;

import me.shreyasr.ancients.EntityFactory;
import me.shreyasr.ancients.Time;
import me.shreyasr.ancients.components.LastUpdateTimeComponent;
import me.shreyasr.ancients.components.PositionComponent;
import me.shreyasr.ancients.components.player.MyPlayerComponent;
import me.shreyasr.ancients.packet.server.ServerAttackPacket;

public class InputActionSystem extends EntitySystem {

    private final PooledEngine engine;
    private final EntityFactory factory;
    private final Client client;
    private Entity player;

    public InputActionSystem(int priority, PooledEngine engine, EntityFactory factory, Client client) {
        super(priority);
        this.engine = engine;
        this.factory = factory;
        this.client = client;
    }

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(MyPlayerComponent.class).get()).get(0);
    }

    int cooldown = 0;

    @Override
    public void update(float deltaTime) {
        PositionComponent pos = PositionComponent.MAPPER.get(player);

        cooldown += deltaTime;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && cooldown > 300) {
            cooldown = 0;
            int dir = getAttackDir(pos, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            Entity newAttack = factory.createSwordSlash(engine, player, pos.x, pos.y, dir);
            newAttack.add(LastUpdateTimeComponent.create(
                    Time.getMillis() + client.getReturnTripTime() + ServerAttackPacket.ATTACK_DELAY_MS));
            engine.addEntity(newAttack);

            Component[] newAttackComponents = newAttack.getComponents().toArray(Component.class);
            client.sendUDP(ServerAttackPacket.create(newAttackComponents));
        }
    }

    private int getAttackDir(PositionComponent pos, int x, int y) {
        float dx = x - pos.x;
        float dy = y - pos.y;

        if (dx >= 0 && dy >= 0) return 0;
        if (dx < 0 && dy >= 0) return 2;
        if (dx < 0 && dy < 0) return 4;
        if (dx >= 0 && dy < 0) return 6;
        else return -1; // impossible, compiler pls
    }
}
