package example.spring.data.nosql.hbase;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HBaseHelper implements Closeable {

    // 一个线程中往表中连续读写数据，建议每个线程只初始化Table对象一次，每次读写共用该对象
    private Table table = null;

    protected HBaseHelper(Table table) {
        this.table = table;
    }

    public static HBaseHelper newInstance(Table table) {
        return new HBaseHelper(table);
    }

    @Override
    public void close() {
        if (null == this.table) {
            return;
        }
        IoUtil.close(this.table);
    }

    /**
     * 删除指定 rowkey 的数据
     *
     * @param row rowkey
     */
    public void deleteRow(String row) {
        try {
            Delete delete = new Delete(Bytes.toBytes(row));
            table.delete(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询单条数据
     *
     * @param rowKey
     * @param cf
     * @param column
     * @return
     */
    public String get(String rowKey, String cf, String column) {
        String value = null;
        try {
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get);
            value = Bytes.toString(result.getValue(Bytes.toBytes(cf), Bytes.toBytes(column)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * scan查询
     *
     * @param startRow
     * @param endRow
     * @param cf
     * @param column
     * @return
     */
    public List<String> scan(String startRow, String endRow, String cf, String column) {
        List<String> list = new ArrayList<>();
        try {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(endRow));
            ResultScanner rs = table.getScanner(scan);
            Result result = rs.next();
            // 获取rowkey
            result.getRow();
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                cell.getQualifierArray();
            }
            while (result != null) {
                String value = Bytes.toString(result.getValue(Bytes.toBytes(cf), Bytes.toBytes(column)));
                list.add(value);
                result = rs.next();
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 过滤器扫描（返回过滤器指定条件的记录，即类似于搜索功能）
     *
     * @throws IOException
     */
    public void filterScan(String cf, String qualifyName) throws IOException {
        //查找姓名为 zhangsan 的学生信息
        SingleColumnValueFilter filter =
            new SingleColumnValueFilter(Bytes.toBytes(cf), Bytes.toBytes(qualifyName), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("zhangsan"));
        //注意，在数据量大但情况下，该功能虽然能实现，但性能很差
        //所以 scan 时最好再指定 startRow 和 stopRow
        Scan scan = new Scan();
        scan.setFilter(filter);
        Table table = null;
        ResultScanner rs = null;
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                String id = Bytes.toString(r.getRow());
                System.out.println("stu id=" + id);
            }
        } finally {
            if (null != rs) {
                rs.close();
            }
        }
    }

    public void fillTable(int startRow, int endRow, int numCols, String... cfs) throws IOException {
        fillTable(startRow, endRow, numCols, -1, false, cfs);
    }

    public void fillTable(int startRow, int endRow, int numCols, boolean setTimestamp, String... cfs)
        throws IOException {
        fillTable(startRow, endRow, numCols, -1, setTimestamp, cfs);
    }

    public void fillTable(int startRow, int endRow, int numCols, int pad, boolean setTimestamp, String... cfs)
        throws IOException {
        fillTable(startRow, endRow, numCols, pad, setTimestamp, false, cfs);
    }

    public void fillTable(int startRow, int endRow, int numCols,
        int pad, boolean setTimestamp, boolean random, String... cfs) throws IOException {
        Random rnd = new Random();
        for (int row = startRow; row <= endRow; row++) {
            for (int col = 1; col <= numCols; col++) {
                Put put = new Put(Bytes.toBytes("row-" + padNum(row, pad)));
                for (String cf : cfs) {
                    String colName = "col-" + padNum(col, pad);
                    String val = "val-" + (random ?
                        Integer.toString(rnd.nextInt(numCols)) :
                        padNum(row, pad) + "." + padNum(col, pad));
                    if (setTimestamp) {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName), col,
                            Bytes.toBytes(val));
                    } else {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName),
                            Bytes.toBytes(val));
                    }
                }
                table.put(put);
            }
        }
    }

    public void fillTableRandom(int minRow, int maxRow, int padRow,
        int minCol, int maxCol, int padCol,
        int minVal, int maxVal, int padVal,
        boolean setTimestamp, String... colfams)
        throws IOException {
        Random rnd = new Random();
        int maxRows = minRow + rnd.nextInt(maxRow - minRow);
        for (int row = 0; row < maxRows; row++) {
            int maxCols = minCol + rnd.nextInt(maxCol - minCol);
            for (int col = 0; col < maxCols; col++) {
                int rowNum = rnd.nextInt(maxRow - minRow + 1);
                Put put = new Put(Bytes.toBytes("row-" + padNum(rowNum, padRow)));
                for (String cf : colfams) {
                    int colNum = rnd.nextInt(maxCol - minCol + 1);
                    String colName = "col-" + padNum(colNum, padCol);
                    int valNum = rnd.nextInt(maxVal - minVal + 1);
                    String val = "val-" + padNum(valNum, padCol);
                    if (setTimestamp) {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName), col,
                            Bytes.toBytes(val));
                    } else {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(colName),
                            Bytes.toBytes(val));
                    }
                }
                table.put(put);
            }
        }
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

    public void put(String row, String family, String column, String value) {
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
        put(put);
    }

    public void put(String row, String family, String column, long ts, String val) throws IOException {
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), ts,
            Bytes.toBytes(val));
        table.put(put);
    }

    public void put(String row, String family, Object obj) {
        Put put = new Put(Bytes.toBytes(row));
        Map<String, Object> objectMap = BeanUtil.beanToMap(obj);
        objectMap.forEach((key, value) -> {
            if (value != null) {
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(key), Bytes.toBytes(String.valueOf(value)));
            }
        });
        put(put);
    }

    // public void put(String[] rows, String[] fams, String[] quals, long[] ts, String[] vals) throws IOException {
    //     for (String row : rows) {
    //         Put put = new Put(Bytes.toBytes(row));
    //         for (String fam : fams) {
    //             int v = 0;
    //             for (String qual : quals) {
    //                 String val = vals[v < vals.length ? v : vals.length - 1];
    //                 long t = ts[v < ts.length ? v : ts.length - 1];
    //                 System.out.println("Adding: " + row + " " + fam + " " + qual +
    //                     " " + t + " " + val);
    //                 put.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual), t,
    //                     Bytes.toBytes(val));
    //                 v++;
    //             }
    //         }
    //         table.put(put);
    //     }
    // }

    public void put(Put put) {
        try {
            table.put(put);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[] put(List<Put> batch) {
        Object[] result = new Object[batch.size()];
        try {
            table.batch(batch, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void dump(String[] rows, String[] fams, String[] quals) throws IOException {
        List<Get> gets = new ArrayList<Get>();
        for (String row : rows) {
            Get get = new Get(Bytes.toBytes(row));
            get.setMaxVersions();
            if (fams != null) {
                for (String fam : fams) {
                    for (String qual : quals) {
                        get.addColumn(Bytes.toBytes(fam), Bytes.toBytes(qual));
                    }
                }
            }
            gets.add(get);
        }
        Result[] results = table.get(gets);
        for (Result result : results) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell +
                    ", Value: " + Bytes.toString(cell.getValueArray(),
                    cell.getValueOffset(), cell.getValueLength()));
            }
        }
    }

    public void dump() throws IOException {
        try (
            ResultScanner scanner = table.getScanner(new Scan())
        ) {
            for (Result result : scanner) {
                dumpResult(result);
            }
        }
    }

    public void dumpResult(Result result) {
        for (Cell cell : result.rawCells()) {
            System.out.println("Cell: " + cell +
                ", Value: " + Bytes.toString(cell.getValueArray(),
                cell.getValueOffset(), cell.getValueLength()));
        }
    }

}
