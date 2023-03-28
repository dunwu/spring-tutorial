package example.spring.data.nosql.hbase;

import cn.hutool.core.io.IoUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HBase admin 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-03-27
 */
public class HBaseAdminHelper implements Closeable {

    private Configuration configuration = null;
    private Connection connection = null;
    private Admin admin = null;

    protected HBaseAdminHelper(Configuration configuration) throws IOException {
        this.configuration = configuration;
        this.connection = ConnectionFactory.createConnection(configuration);
        this.admin = connection.getAdmin();
    }

    public synchronized static HBaseAdminHelper newInstance(Configuration configuration) throws IOException {
        return new HBaseAdminHelper(configuration);
    }

    @Override
    public synchronized void close() {
        if (null == connection || connection.isClosed()) {
            return;
        }
        IoUtil.close(connection);
    }

    public Connection getConnection() {
        if (null == connection) {
            throw new RuntimeException("HBase connection init failed...");
        }
        return connection;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void createNamespace(String namespace) throws IOException {
        NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(nd);
    }

    public void dropNamespace(String namespace) throws IOException {
        dropNamespace(namespace, false);
    }

    public void dropNamespace(String namespace, boolean force) throws IOException {
        if (force) {
            TableName[] tableNames = admin.listTableNamesByNamespace(namespace);
            for (TableName name : tableNames) {
                admin.disableTable(name);
                admin.deleteTable(name);
            }
        }
        admin.deleteNamespace(namespace);
    }

    public boolean existsTable(String tableName) throws IOException {
        return existsTable(TableName.valueOf(tableName));
    }

    public boolean existsTable(TableName tableName) throws IOException {
        return admin.tableExists(tableName);
    }

    public void createTable(String tableName, String... families) throws IOException {
        createTable(TableName.valueOf(tableName), null, families);
    }

    public void createTable(TableName tableName, String... families) throws IOException {
        createTable(tableName, null, families);
    }

    public void createTable(String tableName, byte[][] splitKeys, String... families) throws IOException {
        createTable(TableName.valueOf(tableName), splitKeys, families);
    }

    public void createTable(TableName tableName, byte[][] splitKeys, String... families) throws IOException {

        List<ColumnFamilyDescriptor> columnFamilyDescriptorList = new ArrayList<>();
        TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);
        for (String cf : families) {
            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.of(cf);
            columnFamilyDescriptorList.add(columnFamilyDescriptor);
        }
        builder.setColumnFamilies(columnFamilyDescriptorList);

        TableDescriptor td = builder.build();
        if (splitKeys != null) {
            admin.createTable(td, splitKeys);
        } else {
            admin.createTable(td);
        }
    }

    public void disableTable(String tableName) throws IOException {
        disableTable(TableName.valueOf(tableName));
    }

    public void disableTable(TableName tableName) throws IOException {
        admin.disableTable(tableName);
    }

    public void dropTable(String tableName) throws IOException {
        dropTable(TableName.valueOf(tableName));
    }

    public void dropTable(TableName tableName) throws IOException {
        if (existsTable(tableName)) {
            if (admin.isTableEnabled(tableName)) disableTable(tableName);
            admin.deleteTable(tableName);
        }
    }

    public Table getTable(String tableName) throws IOException {
        return getTable(TableName.valueOf(tableName));
    }

    public Table getTable(TableName tableName) throws IOException {
        return getConnection().getTable(tableName);
    }

}
