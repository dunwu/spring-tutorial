package example.spring.data.nosql.hbase;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.DefaultEvictionPolicy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * HBase CRUD 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-03-27
 */
@Slf4j
public class HBaseHelper implements Closeable {

    private final Connection connection;
    private final Configuration configuration;

    private final LoadingCache<String, HBaseTablePool> tablePoolCache =
        Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(100)
                .expireAfterWrite(Duration.ofMinutes(5))
                .refreshAfterWrite(Duration.ofMinutes(1))
                .build(new CacheLoader<String, HBaseTablePool>() {
                    @Override
                    public HBaseTablePool load(String tableName) {
                        HBaseTablePoolFactory factory =
                            new HBaseTablePoolFactory(connection, TableName.valueOf(tableName));
                        GenericObjectPoolConfig<Table> config = new GenericObjectPoolConfig<>();
                        config.setMaxTotal(100);
                        config.setMinIdle(10);
                        config.setBlockWhenExhausted(true);
                        config.setTimeBetweenEvictionRuns(Duration.ofMinutes(10));
                        config.setSoftMinEvictableIdleTime(Duration.ofMinutes(5));
                        config.setEvictionPolicy(new DefaultEvictionPolicy<>());
                        HBaseTablePool pool = new HBaseTablePool(factory, config);
                        log.info("load HBaseTablePool({}) success.", tableName);
                        return pool;
                    }
                });

    protected HBaseHelper(Configuration configuration) throws IOException {
        this.connection = ConnectionFactory.createConnection(configuration);
        this.configuration = configuration;
    }

    protected HBaseHelper(Connection connection) {
        this.connection = connection;
        this.configuration = connection.getConfiguration();
    }

    public static synchronized HBaseHelper newInstance(Configuration configuration) throws IOException {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration can not be null!");
        }
        return new HBaseHelper(configuration);
    }

    public static synchronized HBaseHelper newInstance(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("connection can not be null!");
        }
        return new HBaseHelper(connection);
    }

    /**
     * 关闭内部持有的 HBase Connection 实例
     */
    @Override
    public synchronized void close() {
        tablePoolCache.cleanUp();
        if (null == connection || connection.isClosed()) {
            return;
        }
        IoUtil.close(connection);
    }

    /**
     * 获取 HBase 连接实例
     *
     * @return /
     */
    public Connection getConnection() {
        if (null == connection) {
            throw new RuntimeException("HBase connection init failed...");
        }
        return connection;
    }

    /**
     * 获取 HBase 配置
     *
     * @return /
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 获取 {@link Table} 实例
     *
     * @param tableName 表名
     * @return /
     */
    public Table getTable(String tableName) throws Exception {
        return getTable(TableName.valueOf(tableName));
    }

    /**
     * 获取 {@link Table} 实例
     *
     * @param tableName 表名
     * @return /
     */

    public synchronized Table getTable(TableName tableName) throws Exception {

        if (tableName == null) {
            throw new IllegalArgumentException("tableName can not be null!");
        }

        HBaseTablePool hbaseTablePool = tablePoolCache.get(Bytes.toString(tableName.getName()));
        if (hbaseTablePool == null) {
            throw new IOException(StrUtil.format("table({}) connect failed!", Bytes.toString(tableName.getName())));
        }
        return hbaseTablePool.borrowObject();
    }

    public void fillTable(TableName tableName, int startRow, int stopRow, int colNum, String... families)
        throws Exception {
        fillTable(tableName, startRow, stopRow, colNum, -1, false, families);
    }

    public void fillTable(TableName tableName, int startRow, int stopRow, int colNum, boolean setTimestamp,
        String... families) throws Exception {
        fillTable(tableName, startRow, stopRow, colNum, -1, setTimestamp, families);
    }

    public void fillTable(TableName tableName, int startRow, int stopRow, int colNum, int pad, boolean setTimestamp,
        String... families) throws Exception {
        fillTable(tableName, startRow, stopRow, colNum, pad, setTimestamp, false, families);
    }

    public void fillTable(TableName tableName, int startRow, int stopRow, int colNum, int pad, boolean setTimestamp,
        boolean random, String... families) throws Exception {
        Table table = getTable(tableName);
        Random rnd = new Random();
        for (int row = startRow; row <= stopRow; row++) {
            for (int col = 1; col <= colNum; col++) {
                Put put = new Put(Bytes.toBytes("row-" + padNum(row, pad)));
                for (String family : families) {
                    String colName = "col-" + padNum(col, pad);
                    String value = "value-" + (random ? Integer.toString(rnd.nextInt(colNum))
                        : padNum(row, pad) + "." + padNum(col, pad));
                    if (setTimestamp) {
                        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(colName), col, Bytes.toBytes(value));
                    } else {
                        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(colName), Bytes.toBytes(value));
                    }
                }
                table.put(put);
            }
        }
        recycle(table);
    }

    public void fillTableRandom(TableName tableName, int minRow, int maxRow, int padRow, int minCol, int maxCol,
        int padCol, int minVal, int maxVal, boolean setTimestamp, String... families) throws Exception {
        Table table = getTable(tableName);
        Random rnd = new Random();
        int maxRows = minRow + rnd.nextInt(maxRow - minRow);
        for (int row = 0; row < maxRows; row++) {
            int maxCols = minCol + rnd.nextInt(maxCol - minCol);
            for (int col = 0; col < maxCols; col++) {
                int rowNum = rnd.nextInt(maxRow - minRow + 1);
                Put put = new Put(Bytes.toBytes("row-" + padNum(rowNum, padRow)));
                for (String family : families) {
                    int colNum = rnd.nextInt(maxCol - minCol + 1);
                    String colName = "col-" + padNum(colNum, padCol);
                    int valNum = rnd.nextInt(maxVal - minVal + 1);
                    String value = "value-" + padNum(valNum, padCol);
                    if (setTimestamp) {
                        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(colName), col, Bytes.toBytes(value));
                    } else {
                        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(colName), Bytes.toBytes(value));
                    }
                }
                table.put(put);
            }
        }
        recycle(table);
    }

    public String padNum(int num, int pad) {
        String res = Integer.toString(num);
        if (pad > 0) {
            while (res.length() < pad) {
                res = "0" + res;
            }
        }
        return res;
    }

    public void put(TableName tableName, String row, String family, String column, String value) throws Exception {
        Table table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
        recycle(table);
    }

    public void put(TableName tableName, String row, String family, String column, long ts, String value)
        throws Exception {
        Table table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), ts, Bytes.toBytes(value));
        table.put(put);
        recycle(table);
    }

    public void put(TableName tableName, String[] rows, String[] families, String[] columns, long[] ts, String[] values)
        throws Exception {
        Table table = getTable(tableName);
        for (String row : rows) {
            Put put = new Put(Bytes.toBytes(row));
            for (String family : families) {
                int v = 0;
                for (String column : columns) {
                    String value = values[v < values.length ? v : values.length - 1];
                    long t = ts[v < ts.length ? v : ts.length - 1];
                    // System.out.println("Adding: " + row + " " + family + " " + column +
                    //     " " + t + " " + value);
                    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), t, Bytes.toBytes(value));
                    v++;
                }
            }
            table.put(put);
        }
        recycle(table);
    }

    @SuppressWarnings("unchecked")
    public void put(TableName tableName, String row, String family, Object obj) throws Exception {
        Map<String, Object> map;
        if (obj instanceof Map) {
            map = (Map<String, Object>) obj;
        } else {
            map = BeanUtil.beanToMap(obj);
        }
        put(tableName, row, family, map);
    }

    public void put(TableName tableName, String row, String family, Map<String, Object> map) throws Exception {
        Put put = new Put(Bytes.toBytes(row));
        map.forEach((key, value) -> {
            if (value != null) {
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(key), Bytes.toBytes(String.valueOf(value)));
            }
        });
        Table table = getTable(tableName);
        table.put(put);
    }

    public void deleteRow(TableName tableName, String row) throws Exception {
        Delete delete = new Delete(Bytes.toBytes(row));
        Table table = getTable(tableName);
        table.delete(delete);
        recycle(table);
    }

    public void dump(TableName tableName, String[] rows, String[] families, String[] columns) throws Exception {
        Table table = getTable(tableName);
        List<Get> gets = new ArrayList<>();
        for (String row : rows) {
            Get get = new Get(Bytes.toBytes(row));
            if (families != null) {
                for (String family : families) {
                    for (String column : columns) {
                        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
                    }
                }
            }
            gets.add(get);
        }
        Result[] results = table.get(gets);
        for (Result result : results) {
            for (Cell cell : result.rawCells()) {
                System.out.println(
                    "Cell: " + cell + ", Value: " + Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength()));
            }
        }
        recycle(table);
    }

    public void dump(TableName tableName) throws Exception {
        Table table = getTable(tableName);
        ResultScanner scanner = table.getScanner(new Scan());
        for (Result result : scanner) {
            dumpResult(result);
        }
        recycle(table);
    }

    /**
     * 指定行、列族、列，返回相应单元中的值
     *
     * @param tableName 表名
     * @param row       指定行
     * @param family    列族
     * @param column    列
     * @return /
     */
    public String get(TableName tableName, String row, String family, String column) throws Exception {
        Get get = new Get(Bytes.toBytes(row));
        Table table = getTable(tableName);
        Result result = table.get(get);
        return Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(column)));
    }

    /**
     * 指定行、列族，以实体 {@link T} 形式返回数据
     *
     * @param tableName 表名
     * @param row       指定行
     * @param family    列族
     * @param clazz     返回实体类型
     * @param <T>       实体类型
     * @return /
     */
    public <T> T get(TableName tableName, String row, String family, Class<T> clazz) throws Exception {

        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(clazz);
        Set<String> columns = fieldMap.keySet();

        List<Get> gets = new ArrayList<>();
        Get get = new Get(Bytes.toBytes(row));
        for (String column : columns) {
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        gets.add(get);

        Table table = getTable(tableName);
        Result[] results = table.get(gets);
        Map<String, String> map = new HashMap<>(columns.size());
        for (Result result : results) {
            for (String column : columns) {
                String value = Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(column)));
                if (value != null) {
                    map.put(column, value);
                }
            }
        }
        T obj = BeanUtil.mapToBean(map, clazz, true, CopyOptions.create().ignoreError());
        recycle(table);
        return obj;
    }

    /**
     * 指定行、列族，多个列，并以 {@link Map} 形式返回
     *
     * @param tableName 表名
     * @param row       指定行
     * @param family    列族
     * @param columns   指定列
     * @return /
     */
    public Map<String, String> get(TableName tableName, String row, String family, Collection<String> columns)
        throws Exception {

        List<Get> gets = new ArrayList<>();
        Get get = new Get(Bytes.toBytes(row));
        for (String column : columns) {
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        gets.add(get);

        Table table = getTable(tableName);
        Result[] results = table.get(gets);
        Map<String, String> map = new HashMap<>(columns.size());
        for (Result result : results) {
            for (String column : columns) {
                String value = Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(column)));
                if (value != null) {
                    map.put(column, value);
                }
            }
        }
        recycle(table);
        return map;
    }

    /**
     * 指定列族、多个列，进行全表范围查询
     *
     * @param tableName 表名
     * @param family    列族
     * @param columns   将要返回的列（未指定的列不会返回）
     * @return 一级 Map 的 key 是 Row Key，value 是二级 Map；二级 Map 的 key 是列，value 是列值
     */
    public Map<String, Map<String, String>> scan(TableName tableName, String family, Collection<String> columns)
        throws Exception {
        Scan scan = new Scan();
        for (String column : columns) {
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        return scan(tableName, family, columns, scan);
    }

    /**
     * 指定起止行、列族、多个列，进行范围查询
     *
     * @param startRow  起始行
     * @param stopRow   结束行
     * @param tableName 表名
     * @param family    列族
     * @param columns   将要返回的列（未指定的列不会返回）
     * @return 一级 Map 的 key 是 Row Key，value 是二级 Map；二级 Map 的 key 是列，value 是列值
     */
    public Map<String, Map<String, String>> scan(TableName tableName, String startRow, String stopRow, String family,
        Collection<String> columns) throws Exception {
        Scan scan = new Scan();
        for (String column : columns) {
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        scan.withStartRow(Bytes.toBytes(startRow));
        scan.withStopRow(Bytes.toBytes(stopRow));
        return scan(tableName, family, columns, scan);
    }

    /**
     * 指定列族、多个列、{@link Filter}，进行全表范围查询
     *
     * @param tableName 表名
     * @param family    列族
     * @param columns   将要返回的列（未指定的列不会返回）
     * @param filter    {@link Filter} 实体
     * @return 一级 Map 的 key 是 Row Key，value 是二级 Map；二级 Map 的 key 是列，value 是列值
     */
    public Map<String, Map<String, String>> scan(TableName tableName, String family, Collection<String> columns,
        Filter filter) throws Exception {
        Scan scan = new Scan();
        for (String column : columns) {
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        scan.setFilter(filter);
        return scan(tableName, family, columns, scan);
    }

    /**
     * 指定起止行、列族、多个列、{@link Filter}，进行范围查询
     *
     * @param startRow  起始行
     * @param stopRow   结束行
     * @param tableName 表名
     * @param family    列族
     * @param columns   将要返回的列（未指定的列不会返回）
     * @param filter    {@link Filter} 实体
     * @return 一级 Map 的 key 是 Row Key，value 是二级 Map；二级 Map 的 key 是列，value 是列值
     */
    public Map<String, Map<String, String>> scan(TableName tableName, String startRow, String stopRow, String family,
        Collection<String> columns, Filter filter) throws Exception {
        Scan scan = new Scan();
        for (String column : columns) {
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        scan.withStartRow(Bytes.toBytes(startRow));
        scan.withStopRow(Bytes.toBytes(stopRow));
        scan.setFilter(filter);
        return scan(tableName, family, columns, scan);
    }

    /**
     * 指定起止写入时间、列族、多个列、{@link Filter}，进行范围查询
     * <p>
     * 注：根据时间范围查询时，会强制按时序倒序排列
     *
     * @param tableName 表名
     * @param family    列族
     * @param columns   将要返回的列（未指定的列不会返回）
     * @param minStamp  起始写入时间
     * @param maxStamp  结束写入时间
     * @param filter    {@link Filter} 实体
     * @return 一级 Map 的 key 是 Row Key，value 是二级 Map；二级 Map 的 key 是列，value 是列值
     */
    public Map<String, Map<String, String>> scan(TableName tableName, String family, Collection<String> columns,
        long minStamp, long maxStamp, Filter filter) throws Exception {
        Scan scan = new Scan();
        for (String column : columns) {
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }
        scan.setTimeRange(minStamp, maxStamp);
        scan.setReversed(true);
        scan.setFilter(filter);
        return scan(tableName, family, columns, scan);
    }

    /**
     * 指定列族、多个列、{@link Scan}，进行范围查询
     *
     * @param tableName 表名
     * @param family    列族
     * @param columns   将要返回的列（未指定的列不会返回）
     * @param scan      {@link Scan} 实体
     * @return 一级 Map 的 key 是 Row Key，value 是二级 Map；二级 Map 的 key 是列，value 是列值
     */
    public Map<String, Map<String, String>> scan(TableName tableName, String family, Collection<String> columns,
        Scan scan) throws Exception {

        Table table = getTable(tableName);
        ResultScanner rs = table.getScanner(scan);
        Result result = rs.next();
        for (Cell cell : result.listCells()) {
            cell.getQualifierArray();
        }

        Map<String, Map<String, String>> map = new LinkedHashMap<>();
        while (result != null) {
            Map<String, String> columnMap = new HashMap<>(columns.size());
            for (String column : columns) {
                String value = Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(column)));
                if (value != null) {
                    columnMap.put(column, value);
                }
            }
            map.put(Bytes.toString(result.getRow()), columnMap);
            result = rs.next();
        }
        rs.close();
        recycle(table);
        return map;
    }

    public void dumpResult(Result result) {
        for (Cell cell : result.rawCells()) {
            String msg = StrUtil.format("Cell: {}, Value: {}", cell,
                Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            System.out.println(msg);
        }
    }

    private void recycle(Table table) {
        if (table != null) {
            HBaseTablePool tablePool = tablePoolCache.getIfPresent(table.getName());
            if (tablePool != null) {
                tablePool.returnObject(table);
            }
        }
    }

}
