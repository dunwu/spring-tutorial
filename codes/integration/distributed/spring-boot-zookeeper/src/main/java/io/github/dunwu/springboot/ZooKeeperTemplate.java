package io.github.dunwu.springboot;// import java classes

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperTemplate {

    final CountDownLatch connectedSignal = new CountDownLatch(1);

    // declare zookeeper instance to access ZooKeeper ensemble
    private ZooKeeper zk;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        // znode path
        String path = "/MyFirstZnode"; // Assign path to znode

        // data in byte array
        byte[] data = "My first zookeeper app".getBytes(); // Declare data

        ZooKeeperTemplate zooKeeperTemplate = new ZooKeeperTemplate();
        ZooKeeper zk = zooKeeperTemplate.connect("172.22.6.9,172.22.6.10,172.22.6.11");
        // zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 判断 znode 是否存在
        Stat stat = zk.exists(path, true);
        if (stat != null) {
            System.out.println("Node exists and the node version is " + stat.getVersion());
        } else {
            System.out.println("Node does not exists");
        }

        // 获取 znode 数据
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        byte[] b = zk.getData(path, watcher -> {

            if (watcher.getType() == Watcher.Event.EventType.None) {
                if (watcher.getState() == KeeperState.Expired) {
                    connectedSignal.countDown();
                }
            } else {
                String path1 = "/MyFirstZnode";

                byte[] bn = new byte[0];
                try {
                    bn = zk.getData(path1, false, null);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
                String data1 = new String(bn, StandardCharsets.UTF_8);
                System.out.println(data1);
                connectedSignal.countDown();
            }
        }, null);
        String queryResult = new String(b, StandardCharsets.UTF_8);
        System.out.println(queryResult);
        connectedSignal.await();

        zk.setData(path, "Success".getBytes(), zk.exists(path, false).getVersion());

        zooKeeperTemplate.close();
    }

    // Method to connect zookeeper ensemble.
    public ZooKeeper connect(String host) throws IOException, InterruptedException {

        zk = new ZooKeeper(host, 5000, watcher -> {
            if (watcher.getState() == KeeperState.SyncConnected) {
                connectedSignal.countDown();
            }
        });

        connectedSignal.await();
        return zk;
    }

    // Method to disconnect from zookeeper server
    public void close() throws InterruptedException {
        zk.close();
    }

    public void create(String path, byte[] data) throws
        KeeperException, InterruptedException {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
            CreateMode.PERSISTENT);
    }

}
