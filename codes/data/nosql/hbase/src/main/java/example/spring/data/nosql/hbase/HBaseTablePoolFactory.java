package example.spring.data.nosql.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

/**
 * HBase {@link Table} 池化对象工厂类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-03-28
 */
@Slf4j
public class HBaseTablePoolFactory extends BasePooledObjectFactory<Table> {

    private final TableName tableName;
    private final Connection connection;

    public HBaseTablePoolFactory(Connection connection, TableName tableName) {
        if (connection == null) {
            throw new IllegalArgumentException("connection can not be null!");
        }
        if (tableName == null) {
            throw new IllegalArgumentException("tableName can not be blank!");
        }
        this.connection = connection;
        this.tableName = tableName;
    }

    @Override
    public Table create() throws Exception {
        return connection.getTable(tableName);
    }

    @Override
    public PooledObject<Table> wrap(Table table) {
        return new DefaultPooledObject<>(table);
    }

    @Override
    public void destroyObject(PooledObject<Table> pooledObject, DestroyMode destroyMode) {
        if (pooledObject.getObject() == null) {
            return;
        }
        try {
            pooledObject.getObject().close();
        } catch (Exception e) {
            // 静默关闭
        }
        log.info("hbase table({}) connection closed.", pooledObject.getObject().getName());
    }

}
