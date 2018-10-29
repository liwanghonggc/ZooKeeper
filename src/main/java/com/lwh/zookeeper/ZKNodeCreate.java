package com.lwh.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

/**
 * @author lwh
 * @date 2018-10-29
 * @desp demo3:创建节点
 */
public class ZKNodeCreate implements Watcher {

    private ZooKeeper zooKeeper = null;

    private static final String zkServerPath = "47.101.208.194:2181";

    private static final int timeout = 5000;

    public ZKNodeCreate() {

    }

    public ZKNodeCreate(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZKNodeCreate());
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
     *                   Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
     *                   CREATOR_ALL_ACL --> auth:user:password:cdrwa
     *        createMode 节点类型,是一个枚举,
     *                   persistent:持久节点
     *                   persistent_sequential:持久顺序节点
     *                   ephemeral:临时节点
     *                   ephemeral_sequential:持久顺序节点
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls){
        String result = "";
        try{
            //同步方法,临时节点
            result = zooKeeper.create(path, data, acls, CreateMode.EPHEMERAL);

            //异步方式,ctx这里可以做一些操作,如发送邮件等
            String ctx = "{'create':'success'}";
            //创建永久节点
            zooKeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallback(), ctx);

            System.out.println("创建节点: " + result + " 成功");
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZKNodeCreate zkNodeOperator = new ZKNodeCreate(zkServerPath);
        //创建zk节点,最低级的权限
        zkNodeOperator.createZKNode("/testnode", "testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
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
