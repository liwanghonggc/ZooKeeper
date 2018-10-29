package com.lwh.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author lwh
 * @date 2018-10-29
 * @desp demo4:修改和删除节点
 */
public class ZKNodeUpdate implements Watcher {

    private ZooKeeper zooKeeper = null;

    private static final String zkServerPath = "47.101.208.194:2181";

    private static final int timeout = 5000;

    public ZKNodeUpdate() {

    }

    public ZKNodeUpdate(String connectString){
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZKNodeUpdate());
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

    public static void main(String[] args) throws Exception {
        ZKNodeUpdate zkServer = new ZKNodeUpdate(zkServerPath);

        //修改节点,最后一个参数1表示版本号,Stat就是我们get /testnode得到的信息的封装
        Stat stat = zkServer.getZooKeeper().setData("/testnode", "modify".getBytes(), 1);
        System.out.println(stat.getVersion());

        //删除节点
        zkServer.getZooKeeper().delete("/testnode", 2);
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
