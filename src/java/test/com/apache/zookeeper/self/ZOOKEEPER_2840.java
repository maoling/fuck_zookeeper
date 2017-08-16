package com.apache.zookeeper.self;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.apache.zookeeper.client.StaticHostProvider;
import org.junit.Test;

public class ZOOKEEPER_2840 {

	@Test
	public void testShuffle() throws Exception {
        LinkedList<InetSocketAddress> inetSocketAddressesList = new LinkedList<>();
        inetSocketAddressesList.add(new InetSocketAddress(0));
        inetSocketAddressesList.add(new InetSocketAddress(1));
        inetSocketAddressesList.add(new InetSocketAddress(2));
        /*
        1442045361
        currentTime: 1499253530044, currentTime ^ hashCode: 1500143845389, Result: 1 2 0
        currentTime: 1499253530044, currentTime ^ hashCode: 1500143845389, Result: 2 0 1
        currentTime: 1499253530045, currentTime ^ hashCode: 1500143845388, Result: 0 1 2
        currentTime: 1499253530045, currentTime ^ hashCode: 1500143845388, Result: 1 2 0
        currentTime: 1499253530046, currentTime ^ hashCode: 1500143845391, Result: 1 2 0
        currentTime: 1499253530046, currentTime ^ hashCode: 1500143845391, Result: 1 2 0
        currentTime: 1499253530046, currentTime ^ hashCode: 1500143845391, Result: 1 2 0
        currentTime: 1499253530046, currentTime ^ hashCode: 1500143845391, Result: 1 2 0
        currentTime: 1499253530047, currentTime ^ hashCode: 1500143845390, Result: 1 2 0
        currentTime: 1499253530047, currentTime ^ hashCode: 1500143845390, Result: 1 2 0
         */
        //internalShuffleMillis(inetSocketAddressesList);
        /*
        146611050
        currentTime: 22618159623770, currentTime ^ hashCode: 22618302559536, Result: 2 1 0
        currentTime: 22618159800738, currentTime ^ hashCode: 22618302085832, Result: 0 1 2
        currentTime: 22618159967442, currentTime ^ hashCode: 22618302248888, Result: 1 0 2
        currentTime: 22618160135080, currentTime ^ hashCode: 22618302013634, Result: 2 1 0
        currentTime: 22618160302095, currentTime ^ hashCode: 22618301535077, Result: 2 1 0
        currentTime: 22618160490260, currentTime ^ hashCode: 22618301725822, Result: 1 0 2
        currentTime: 22618161566373, currentTime ^ hashCode: 22618300303823, Result: 1 0 2
        currentTime: 22618161745518, currentTime ^ hashCode: 22618300355844, Result: 2 1 0
        currentTime: 22618161910357, currentTime ^ hashCode: 22618291603775, Result: 2 1 0
        currentTime: 22618162079549, currentTime ^ hashCode: 22618291387479, Result: 0 1 2
         */
        //internalShuffleNano(inetSocketAddressesList);

        inetSocketAddressesList.clear();
        inetSocketAddressesList.add(new InetSocketAddress(0));
        inetSocketAddressesList.add(new InetSocketAddress(1));

        /*
        415138788
        
        currentTime: 1499253530050, currentTime ^ hashCode: 1499124456998, Result: 0 1
        currentTime: 1499253530050, currentTime ^ hashCode: 1499124456998, Result: 0 1
        currentTime: 1499253530050, currentTime ^ hashCode: 1499124456998, Result: 0 1
        currentTime: 1499253530050, currentTime ^ hashCode: 1499124456998, Result: 0 1
        currentTime: 1499253530050, currentTime ^ hashCode: 1499124456998, Result: 0 1
        currentTime: 1499253530050, currentTime ^ hashCode: 1499124456998, Result: 0 1
        currentTime: 1499253530053, currentTime ^ hashCode: 1499124456993, Result: 0 1
        currentTime: 1499253530055, currentTime ^ hashCode: 1499124456995, Result: 0 1
        currentTime: 1499253530055, currentTime ^ hashCode: 1499124456995, Result: 0 1
        currentTime: 1499253530055, currentTime ^ hashCode: 1499124456995, Result: 0 1
         */
        internalShuffleMillis(inetSocketAddressesList);
        /*
        13326370
        currentTime: 22618168292396, currentTime ^ hashCode: 22618156149774, Result: 1 0
        currentTime: 22618168416181, currentTime ^ hashCode: 22618156535703, Result: 1 0
        currentTime: 22618168534056, currentTime ^ hashCode: 22618156432394, Result: 0 1
        currentTime: 22618168666548, currentTime ^ hashCode: 22618155774358, Result: 0 1
        currentTime: 22618168818946, currentTime ^ hashCode: 22618155623712, Result: 0 1
        currentTime: 22618168936821, currentTime ^ hashCode: 22618156011863, Result: 1 0
        currentTime: 22618169056251, currentTime ^ hashCode: 22618155893721, Result: 1 0
        currentTime: 22618169611103, currentTime ^ hashCode: 22618157370237, Result: 1 0
        currentTime: 22618169744528, currentTime ^ hashCode: 22618156713138, Result: 1 0
        currentTime: 22618171273170, currentTime ^ hashCode: 22618184562672, Result: 1 0
         */
        //internalShuffleNano(inetSocketAddressesList);
    }

    private void internalShuffleMillis(LinkedList<InetSocketAddress> inetSocketAddressesList) throws Exception {
        int hashCode = new StaticHostProvider(inetSocketAddressesList).hashCode();
        System.out.println(hashCode);
        int count = 60;
        int index = 0;
        Random r;
        while (count > 0) {
            long currentTime = System.currentTimeMillis();
            r = new Random(currentTime ^ hashCode);
            System.out.print(String.format("currentMillisTime: %s, currentMillisTime ^ hashCode: %s, Result: ",
                    currentTime, currentTime ^ hashCode));
            Collections.shuffle(inetSocketAddressesList, r);
            for (int i = 0; i < inetSocketAddressesList.size(); i++) {
                System.out.print(String.format("%s ", inetSocketAddressesList.get(i).getPort()));
            }   
            if (inetSocketAddressesList.get(0).getPort() == 0) index++; 
            count--;
            System.out.println();
        }
        System.out.println("the rate of 0 is: " + index);
    }

    private void internalShuffleNano(LinkedList<InetSocketAddress> inetSocketAddressesList) throws Exception {
        int hashCode = new StaticHostProvider(inetSocketAddressesList).hashCode();
        System.out.println(hashCode);
        int count = 10;
        Random r;
        while (count > 0) {
            long currentTime = System.nanoTime();
            r = new Random(currentTime ^ hashCode);
            System.out.print(String.format("currentNanoTime: %s, currentNanoTime ^ hashCode: %s, Result: ",
                    currentTime, currentTime ^ hashCode));
            Collections.shuffle(inetSocketAddressesList, r);
            for (InetSocketAddress inetSocketAddress : inetSocketAddressesList) {
                System.out.print(String.format("%s ", inetSocketAddress.getPort()));
            }
            System.out.println();
            count--;
        }
    }
	
}
