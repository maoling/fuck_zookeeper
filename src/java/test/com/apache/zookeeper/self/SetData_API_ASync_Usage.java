package com.apache.zookeeper.self;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SetData_API_ASync_Usage implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String path = "/zk-book";
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new SetData_API_ASync_Usage());
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
			//zooKeeper.getData(path, true, null);
			
			zooKeeper.setData(path, "456".getBytes(), -1, new IStatCallback(), null);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //会话超时时间为5秒;需要等待一点时间异步回调IStatCallback
	    Thread.sleep(Integer.MAX_VALUE);
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

class IStatCallback implements AsyncCallback.StatCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		// TODO Auto-generated method stub
		System.out.println("rc:" + rc);
		if (rc == 0) {
			System.out.println("SUCCESS");
		}
	}		
}
