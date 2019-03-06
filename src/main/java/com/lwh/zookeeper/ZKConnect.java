package com.lwh.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;



/**
 * @author lwh
 * @date 2018-10-28
 * @desp demo1:zookeeper连接演示
 */
public class ZKConnect implements Watcher {

    private static final String zkServerPath = "47.101.208.194:2181";

    private static final int timeout = 5000;

    /**
     * 客户端和zk服务端的连接是一个异步的过程,当连接成功之后,客户端会收到一个watch通知
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath, timeout, new ZKConnect());
        System.out.println("客户端开始连接zookeeper服务器...");

        System.out.println("连接状态: " + zooKeeper.getState());

        //等待watcher事件
        Thread.sleep(2000);

        System.out.println("连接状态: " + zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("接收到watcher通知: " + event);
    }
}
