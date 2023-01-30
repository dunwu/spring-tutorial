package example.spring.data.nosql.hdfs;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

/**
 * HDFS 工厂类，需要配合 {@link HdfsPool} 使用
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
public class HdfsFactory extends BasePooledObjectFactory<FileSystem> {

    private Configuration configuration;

    public HdfsFactory(org.apache.hadoop.conf.Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public FileSystem create() throws IOException {
        return FileSystem.get(configuration);
    }

    @Override
    public PooledObject<FileSystem> wrap(FileSystem obj) {
        return new DefaultPooledObject<>(obj);
    }

    @Override
    public void destroyObject(PooledObject<FileSystem> pooledObject) throws IOException {
        if (pooledObject == null) {
            return;
        }

        FileSystem fileSystem = pooledObject.getObject();
        try {
            fileSystem.close();
        } finally {
            fileSystem = null;
        }
    }

    @Override
    public boolean validateObject(final PooledObject<FileSystem> pooledObject) {
        if (pooledObject == null) {
            return false;
        }

        FileSystem obj = pooledObject.getObject();
        return obj != null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
