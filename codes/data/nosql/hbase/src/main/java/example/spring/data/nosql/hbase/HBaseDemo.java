package example.spring.data.nosql.hbase;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

@Slf4j
public class HBaseDemo {

    public static void main(String[] args) throws IOException {

        // 请改为配置的方式
        String zkHosts = "localhost";
        // 请改为配置的方式
        String zkPort = "2181";
        // 请改为配置的方式
        String namespace = "test";
        String tablename = "test";
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zkHosts);
        conf.set("hbase.zookeeper.port", zkPort);

        // 创建命名空间和表
        TableName tableName = TableName.valueOf(namespace, tablename);
        HBaseAdminHelper hBaseAdminHelper = HBaseAdminHelper.newInstance(conf);
        hBaseAdminHelper.createNamespace(namespace);
        hBaseAdminHelper.createTable(tableName, "c1");
        Table table = hBaseAdminHelper.getTable(tableName);

        String rowKey = IdUtil.fastSimpleUUID();
        HBaseHelper hbaseHelper = new HBaseHelper(table);
        hbaseHelper.put(rowKey, "c1", "name", "jack");
        String value = hbaseHelper.get(rowKey, "c1", "name");
        System.out.println("value = " + value);

        hbaseHelper.close();
        hBaseAdminHelper.close();
    }

}
