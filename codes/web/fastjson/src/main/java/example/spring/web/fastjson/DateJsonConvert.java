package example.spring.web.fastjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本类仅为展示如何定制指定类的序列化、反序列化，请勿用于生产环境
 */
@JsonComponent
public class DateJsonConvert {

    /**
     * 对 Date 类做特殊序列化
     */
    public static class DateJsonSerializer extends JsonSerializer<Date> {

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonGenerator.writeObject(sdf.format(date));
        }

    }

    /**
     * InfoDto 类的反序列化做特殊处理
     */
    public static class InfoDTOJsonDeserializer extends JsonDeserializer<InfoDto> {

        @Override
        public InfoDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
            InfoDto infoDTO = new InfoDto();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

            TextNode appNameNode = (TextNode) treeNode.get("appName");
            infoDTO.setAppName(appNameNode.asText());
            TextNode versionNode = (TextNode) treeNode.get("version");
            infoDTO.setVersion(versionNode.asText());
            TextNode dateNode = (TextNode) treeNode.get("date");
            try {
                infoDTO.setDate(sdf.parse(dateNode.asText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return infoDTO;
        }

    }

}
