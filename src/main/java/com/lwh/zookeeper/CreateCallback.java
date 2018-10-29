package com.lwh.zookeeper;

import org.apache.zookeeper.AsyncCallback;

/**
 * @author lwh
 * @date 2018-10-29
 * @desp zk异步创建节点的回调方法
 */
public class CreateCallback implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("创建节点: " + path);
        System.out.println((String)ctx);
    }
}
