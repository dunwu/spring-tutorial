package example.spring.data.nosql.hbase;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.hbase.client.Table;

/**
 * HBase {@link Table} 连接池工具，基于 common-pool2 的 {@link GenericObjectPool} 实现，需要配合 {@link HBaseTablePoolFactory} 使用
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-03-28
 */
public class HBaseTablePool extends GenericObjectPool<Table> {

    public HBaseTablePool(PooledObjectFactory<Table> factory) {
        super(factory);
    }

    public HBaseTablePool(PooledObjectFactory<Table> factory, GenericObjectPoolConfig<Table> config) {
        super(factory, config);
    }

    public HBaseTablePool(PooledObjectFactory<Table> factory, GenericObjectPoolConfig<Table> config,
        AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

}
