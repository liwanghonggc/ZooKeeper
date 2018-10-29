package com.lwh.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

/**
 * @author lwh
 * @date 2018-10-29
 * @desp
 */
public class ZKNodeOperator implements Watcher {

    private ZooKeeper zooKeeper = null;

    private static final String zkServerPath = "47.101.208.194:2181";

    private static final int timeout = 5000;

    public ZKNodeOperator() {

    }

    public ZKNodeOperator(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZKNodeOperator());
        } catch (IOException e) {
            e.printStackTrace();
            if(zooKeeper != null){
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 同步或者异步创建节点,都不支持节点的递归创建,异步有一个callback函数
     * @param path 创建的路径
     * @param data 存储的数据的byte[]
     * @param acls 控制权限策略
     * @param createMode 节点类型,是一个枚举,
     *                   persistent:持久节点
     *                   persistent_sequential:持久顺序节点
     *                   ephemeral:临时节点
     *                   ephemeral_sequential:持久顺序节点
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls){
        String result = "";
        try{
            result = zooKeeper.create(path, data, acls, CreateMode.EPHEMERAL);
        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        ZKNodeOperator zkNodeOperator = new ZKNodeOperator(zkServerPath);

    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    @Override
    public void process(WatchedEvent event) {

    }
}
