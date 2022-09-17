package io.github.dunwu.springboot.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @author Victor Zhang
 * @since 2016/12/23.
 */
public class VelocityUtil {

    private static VelocityEngine velocityEngine;

    static {
        Properties props = new Properties();
        props.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        props.setProperty(RuntimeConstants.INPUT_ENCODING, "utf-8");
        props.setProperty(RuntimeConstants.OUTPUT_ENCODING, "utf-8");
        props.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine = new VelocityEngine();
        velocityEngine.init(props);
    }

    public static String getMergeOutput(VelocityContext context, String templateName) {
        Template template = velocityEngine.getTemplate(templateName);

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String output = sw.toString();
        try {
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

}
