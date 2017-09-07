package com.apache.zookeeper.self;

import org.apache.zookeeper.server.quorum.Learner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ZOOKEEPER_2572 {

	protected static final Logger LOG = LoggerFactory.getLogger(Learner.class);	
	public static void main(String[] args) {
		long zxid = 0;
		try {
		    //zxid = 9999;
            int i = 8 / 0;
        } catch (Exception e) {
            // not able to truncate the log
            //LOG.error("Not able to truncate the log {}, Unexpected exception "
                    //+ Long.toHexString(zxid), e);
        	//e.printStackTrace();
           
        }
		System.out.println("fuck");
	}
}
