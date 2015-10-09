package me.shreyasr.ancients;

import com.esotericsoftware.kryonet.Listener;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LinkedListQueuedListener extends Listener.QueuedListener {

    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<Runnable>();

    public LinkedListQueuedListener() {
        super(null);
    }

    public LinkedListQueuedListener(Listener listener) {
        super(listener);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void runAll() {
        while (!queue.isEmpty()) {
            runOne();
        }
    }

    public void runOne() {
        System.out.println(queue.size() + " " + Thread.currentThread().getName());
        if (!queue.isEmpty()) {
            Runnable r = queue.poll();
            if (r == null) {
                System.out.println("WHOOOOOAAA");
            }
            System.out.println(r);
            r.run();
        }
    }

    public int getQueueSize() {
        return queue.size();
    }

    @Override
    protected void queue(Runnable r) {
        queue.add(r);
    }
}