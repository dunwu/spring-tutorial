package example.spring.data.nosql.hdfs;

import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-14
 */
@RequiredArgsConstructor
@SpringBootApplication(exclude = {
    JmxAutoConfiguration.class
})
public class SpringBootDataHdfsApplication implements CommandLineRunner {

    private final HdfsUtil hdfsUtil;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataHdfsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<LocatedFileStatus> locatedFileStatuses = hdfsUtil.listFiles("/");
        locatedFileStatuses.forEach(System.out::println);
    }

}
