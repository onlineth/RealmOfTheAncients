package me.shreyasr.ancients.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.esotericsoftware.kryonet.Client;
import me.shreyasr.ancients.components.PlayerComponent;
import me.shreyasr.ancients.packet.ServerPlayerUpdatePacket;

public class NetworkUpdateSystem extends EntitySystem {

    private final Client client;
    private Entity player;

    public NetworkUpdateSystem(int priority, Client client) {
        super(priority);
        this.client = client;
    }

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
    }

    public void update(float deltaTime) {
        Component[] componentsArr = player.getComponents().toArray(Component.class);
        client.sendTCP(ServerPlayerUpdatePacket.create(componentsArr));
    }
}