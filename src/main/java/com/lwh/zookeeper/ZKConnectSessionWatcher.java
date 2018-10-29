package com.lwh.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


/**
 * @author lwh
 * @date 2018-10-29
 * @desp demo2:zk恢复之前的会话连接demo演示
 */
public class ZKConnectSessionWatcher implements Watcher {

    private static final String zkServerPath = "47.101.208.194:2181";

    private static final int timeout = 5000;

    /**
     * 在linux命令行里面,使用该命令可以查看会话相关信息: echo dump | nc 47.101.208.194 2181
     */
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(zkServerPath, timeout, new ZKConnectSessionWatcher());

        //获取两个参数用于后面会话重连
        //这两个参数在开发中我们一般获取到后会放到session或者redis中
        long sessionId = zk.getSessionId();
        byte[] sessionPasswd = zk.getSessionPasswd();

        System.out.println("客户端开始连接zookeeper服务器...");
        System.out.println("连接状态: " + zk.getState());
        Thread.sleep(1000);
        System.out.println("连接状态: " + zk.getState());

        Thread.sleep(200);

        System.out.println("开始会话重连...");

        ZooKeeper zkSession = new ZooKeeper(zkServerPath, timeout, new ZKConnectSessionWatcher(), sessionId, sessionPasswd);
        System.out.println("重新连接zkSession状态: " + zkSession.getState());
        Thread.sleep(1000);
        System.out.println("重新连接zkSession状态: " + zkSession.getState());

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("接收到watcher通知: " + event);
    }
}
