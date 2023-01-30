package example.spring.data.nosql.hdfs;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;

/**
 * Hdfs 连接池工具，基于 common-pool2 的 {@link GenericObjectPool} 实现，需要配合 {@link HdfsFactory} 使用
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
public class HdfsPool extends GenericObjectPool<FileSystem> {

    public HdfsPool(final HdfsFactory factory) {
        super(factory);
    }

    public HdfsPool(final HdfsFactory factory, final GenericObjectPoolConfig<FileSystem> config) {
        super(factory, config);
    }

    public HdfsPool(final HdfsFactory factory, final GenericObjectPoolConfig<FileSystem> config,
        final AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

}
