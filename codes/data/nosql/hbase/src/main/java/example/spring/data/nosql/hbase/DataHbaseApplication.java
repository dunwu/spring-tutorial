package example.spring.data.nosql.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataHbaseApplication implements CommandLineRunner {

    @Autowired
    private HBaseAdminHelper hbaseAdminHelper;

    public static void main(String[] args) {
        SpringApplication.run(DataHbaseApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        String namespace = "test";
        String tablename = "test";

        // 创建命名空间和表
        TableName tableName = TableName.valueOf(namespace, tablename);
        if (!hbaseAdminHelper.existsTable(tableName)) {
            hbaseAdminHelper.createNamespace(namespace);
            hbaseAdminHelper.createTable(tableName, "c1");
        }

        HBaseHelper hbaseHelper = HBaseHelper.newInstance(hbaseAdminHelper.getConnection());
        hbaseHelper.put(tableName, "row1", "c1", "name", "tom");
        String value = hbaseHelper.get(tableName, "row1", "c1", "name");
        System.out.println("value = " + value);

        hbaseAdminHelper.close();
    }

}
