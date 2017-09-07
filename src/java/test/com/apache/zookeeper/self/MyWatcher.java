package com.apache.zookeeper.self;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class MyWatcher implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, null);
		//ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new MyWatcher());
	    System.out.println("zooKeeper.getState(): "+zooKeeper.getState());
	    try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("zookeeper session has establised!");
	}
	
	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Receive Watcher Event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			countDownLatch.countDown();
		}	
	}

}
