package com.apache.zookeeper.self;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.client.StaticHostProvider;


public class ZOOKEEPER_2840 {

	private final static int clientThreadNum = 10;
	static AtomicInteger index = new AtomicInteger(0);
	
	public static void main(String[] args)  throws Exception {
		ExecutorService exec = Executors.newFixedThreadPool(clientThreadNum);	
		
		LinkedList<InetSocketAddress> inetSocketAddressesList = new LinkedList<>();
        inetSocketAddressesList.add(new InetSocketAddress(0));
        inetSocketAddressesList.add(new InetSocketAddress(1));
		for(int i = 0;i<clientThreadNum;i++) {
			exec.execute(new Job(inetSocketAddressesList));
		}						
		exec.shutdown();
    }

	static class Job implements Runnable {
		LinkedList<InetSocketAddress> inetSocketAddressesList;
	    
        public Job(LinkedList<InetSocketAddress> inetSocketAddressesList) {
        	this.inetSocketAddressesList = inetSocketAddressesList;
        }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				internalShuffleMillis(inetSocketAddressesList);				
		        //internalShuffleNano(inetSocketAddressesList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void internalShuffleMillis(LinkedList<InetSocketAddress> inetSocketAddressesList) throws Exception {
	        int hashCode = new StaticHostProvider(inetSocketAddressesList).hashCode();
	        System.out.println(hashCode);	        
	        Random r;
	        
        	long currentTime = System.currentTimeMillis();
            r = new Random(currentTime ^ hashCode);
            synchronized (Job.class) {
            	System.out.print(String.format("currentMillisTime: %s, currentMillisTime ^ hashCode: %s,r:%s, Result: ",
                        currentTime, currentTime ^ hashCode, r.nextInt()));
                Collections.shuffle(inetSocketAddressesList, r);
                for (int i = 0; i < inetSocketAddressesList.size(); i++) {
                    System.out.print(String.format("%s ", inetSocketAddressesList.get(i).getPort()));
                }   
                if (inetSocketAddressesList.get(0).getPort() == 0) index.incrementAndGet();             
                System.out.println();
                System.out.println("the count of connecting to 0 is: " + index + "\ttotal client count is :" + clientThreadNum); 	        
			}
            
	    }		
	}
	
    
	
}
