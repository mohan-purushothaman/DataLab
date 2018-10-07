///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.ai.datalab.core.misc;
//
//import com.hazelcast.config.Config;
//import com.hazelcast.config.JoinConfig;
//import com.hazelcast.config.NetworkConfig;
//import com.hazelcast.core.DistributedObject;
//import com.hazelcast.core.Hazelcast;
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.IQueue;
//import java.util.Queue;
//import java.util.concurrent.BlockingQueue;
//import org.ai.datalab.core.Data;
//import org.ai.datalab.core.builder.ExecutionUnit;
//
///**
// *
// * @author Mohan Purushothaman
// */
//public class HazelcastHelper {
//
//    public static final HazelcastInstance PRIVATE_INSTANCE = getPrivateInstance(HazelcastHelper.class.getName());
//
//    private static HazelcastInstance getPrivateInstance(String baseName) {
//        synchronized (HazelcastHelper.class) {
//            int maxAttemps = 100;
//            while (--maxAttemps > 0) {
//                String name = baseName + Math.random();
//                HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName(name);
//                if (instance == null) {
//
//                    Config cfg = new Config(name);
//                    NetworkConfig network = cfg.getNetworkConfig();
//
//                    JoinConfig join = network.getJoin();
//                    join.getTcpIpConfig().setEnabled(false);
//                    join.getAwsConfig().setEnabled(false);
//                    join.getMulticastConfig().setEnabled(false);
//
//                    return Hazelcast.newHazelcastInstance(cfg);
//                }
//            }
//        }
//        throw new RuntimeException("Unable to initiate private Hazelcase instance");
//    }
//
//    public static final BlockingQueue<Data> getPrivateQueue() {
//
//        synchronized (HazelcastHelper.class) {
//            int maxAttemps = 10;
//            while (--maxAttemps > 0) {
//                String name = "" + Math.random() + Math.random();
//
//                if (!queueExists(name)) {
//                    return PRIVATE_INSTANCE.getQueue(name);
//                }
//            }
//        }
//        throw new RuntimeException("Unable to initiate private Hazelcase instance");
//    }
//
//    private static boolean queueExists(String name) {
//        for (DistributedObject d : PRIVATE_INSTANCE.getDistributedObjects()) {
//            if (d instanceof IQueue && d.getName().equals(name)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
