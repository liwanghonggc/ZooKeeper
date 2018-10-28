package com.lwh.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lwh
 * @date 2018-10-28
 * @desp zookeeper连接演示
 */
public class ZKConnetc implements Watcher {

    final static Logger logger = LoggerFactory.getLogger(ZKConnetc.class);

    public static final String zkServerPath = "47.101.208.194:2181";

    public static final int timeout = 5000;


    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath, timeout, new ZKConnetc());
        logger.debug("客户端开始连接zookeeper服务器...");
        logger.debug("连接状态: {}", zooKeeper.getState());

        Thread.sleep(2000);

        logger.debug("连接状态: {}", zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
