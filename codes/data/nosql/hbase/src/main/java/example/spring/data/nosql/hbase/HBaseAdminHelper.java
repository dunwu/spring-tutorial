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

public class HBaseAdminHelper implements Closeable {

    private Configuration configuration = null;
    private Connection connection = null;
    private Admin admin = null;

    protected HBaseAdminHelper(Configuration configuration) throws IOException {
        this.configuration = configuration;
        this.connection = ConnectionFactory.createConnection(configuration);
        this.admin = connection.getAdmin();
    }

    public static HBaseAdminHelper newInstance(Configuration configuration) throws IOException {
        return new HBaseAdminHelper(configuration);
    }

    /**
     * 关闭连接
     */
    @Override
    public void close() {
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

    public void createNamespace(String namespace) {
        try {
            NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(nd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dropNamespace(String namespace) {
        dropNamespace(namespace, false);
    }

    public void dropNamespace(String namespace, boolean force) {
        try {
            if (force) {
                TableName[] tableNames = admin.listTableNamesByNamespace(namespace);
                for (TableName name : tableNames) {
                    admin.disableTable(name);
                    admin.deleteTable(name);
                }
            }
            admin.deleteNamespace(namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existsTable(String tableName) {
        return existsTable(TableName.valueOf(tableName));
    }

    public boolean existsTable(TableName tableName) {
        try {
            return admin.tableExists(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTable(String tableName, String... cfs) {
        createTable(TableName.valueOf(tableName), 1, null, cfs);
    }

    public void createTable(TableName tableName, String... cfs) {
        createTable(tableName, 1, null, cfs);
    }

    public void createTable(String tableName, int maxVersions, String... cfs) {
        createTable(TableName.valueOf(tableName), maxVersions, null, cfs);
    }

    public void createTable(TableName tableName, int maxVersions, String... cfs) {
        createTable(tableName, maxVersions, null, cfs);
    }

    public void createTable(String tableName, byte[][] splitKeys, String... cfs) {
        createTable(TableName.valueOf(tableName), 1, splitKeys, cfs);
    }

    public void createTable(TableName tableName, int maxVersions, byte[][] splitKeys, String... cfs) {

        List<ColumnFamilyDescriptor> columnFamilyDescriptorList = new ArrayList<>();
        TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);
        for (String cf : cfs) {
            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.of(cf);
            columnFamilyDescriptorList.add(columnFamilyDescriptor);
        }
        builder.setColumnFamilies(columnFamilyDescriptorList);

        try {
            TableDescriptor td = builder.build();
            if (splitKeys != null) {
                admin.createTable(td, splitKeys);
            } else {
                admin.createTable(td);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disableTable(String tableName) {
        disableTable(TableName.valueOf(tableName));
    }

    public void disableTable(TableName tableName) {
        try {
            admin.disableTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dropTable(String tableName) {
        dropTable(TableName.valueOf(tableName));
    }

    public void dropTable(TableName tableName) {
        try {
            if (existsTable(tableName)) {
                if (admin.isTableEnabled(tableName)) disableTable(tableName);
                admin.deleteTable(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 table
     *
     * @param tableName 表名
     * @return /
     */
    public Table getTable(String tableName) throws IOException {
        return getTable(TableName.valueOf(tableName));
    }

    /**
     * 获取 table
     *
     * @param tableName 表名对象
     * @return /
     */
    public Table getTable(TableName tableName) throws IOException {
        return getConnection().getTable(tableName);
    }

}
