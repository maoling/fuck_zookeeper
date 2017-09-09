package com.apache.zookeeper.self;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SetData_API_Sync_Usage implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String path = "/zk-book";
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new SetData_API_Sync_Usage());
	    System.out.println("zooKeeper.getState(): "+zooKeeper.getState());
	    try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Stat stat = null;
	    try {
			zooKeeper.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			zooKeeper.getData(path, true, null);
			
			stat = zooKeeper.setData(path, "456".getBytes(), -1);
			System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
	    } catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Stat stat2 = null;
	    try {
			stat2 = zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
			System.out.println(stat2.getCzxid()+","+stat2.getMzxid()+","+stat2.getVersion());
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			//zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
			zooKeeper.setData(path, "456".getBytes(), stat2.getVersion());
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR:" +e.code()+"----"+e.getMessage());
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
