package me.shreyasr.ancients;

import com.badlogic.ashley.core.EntitySystem;

public class PacketHandleSystem extends EntitySystem {

    private final LinkedListQueuedListener queuedListener;

    public PacketHandleSystem(int priority, LinkedListQueuedListener queuedListener) {
        super(priority);
        this.queuedListener = queuedListener;
    }

    private boolean breakingModification;

    @Override
    public void update(float deltaTime) {
        breakingModification = false;
        while (queuedListener.getQueueSize() > 0) {
            queuedListener.runOne();
            if (breakingModification) {
                System.out.println("Breaking mod");
                break;
            }
        }
    }

    public void entityAdded() {
        breakingModification = true;
    }

    public void entityRemoved() {
        breakingModification = true;
    }
}
