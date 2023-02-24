package example.spring.web.fastjson;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 使用 @JsonTest 测试 DateJsonConvert 的配置是否生效
 *
 * @author Zhang Peng
 * @see DateJsonConvert
 * @since 2018-12-30
 */
@JsonTest
public class WebFastjsonTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JacksonTester<InfoDto> json;

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"appName\":\"JSON测试应用\",\"version\":\"1.0.0\",\"date\":\"2019-01-01\"}";
        InfoDto actual = json.parseObject(content);
        assertThat(actual.getAppName()).isEqualTo("JSON测试应用");
        assertThat(actual.getVersion()).isEqualTo("1.0.0");
    }

    @Test
    public void testSerialize() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        InfoDto infoDTO = new InfoDto("JSON测试应用", "1.0.0", sdf.parse("2019-01-01 12:00:00"));
        JsonContent<InfoDto> jsonContent = json.write(infoDTO);
        log.info("json content: {}", jsonContent.getJson());
        // 或者使用基于JSON path的校验
        assertThat(jsonContent).hasJsonPathStringValue("@.appName");
        assertThat(jsonContent).extractingJsonPathStringValue("@.appName").isEqualTo("JSON测试应用");
        assertThat(jsonContent).hasJsonPathStringValue("@.version");
        assertThat(jsonContent).extractingJsonPathStringValue("@.version").isEqualTo("1.0.0");
        assertThat(jsonContent).hasJsonPathStringValue("@.date");
        assertThat(jsonContent).extractingJsonPathStringValue("@.date").isEqualTo("2019-01-01 12:00:00");
    }

}
