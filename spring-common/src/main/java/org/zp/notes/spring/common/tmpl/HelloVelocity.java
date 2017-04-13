package org.zp.notes.spring.common.tmpl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class HelloVelocity {
    public static void main(String[] args) throws IOException {
        HelloVelocity hello = new HelloVelocity();
        hello.loadByClasspath();
        hello.loadByFilepath();
        hello.loadByConfig();
    }

    /**
     * 加载classpath目录下的vm文件
     */
    private void loadByClasspath() {
        System.out.println("========== loadByClasspath ==========");

        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);
        Template t = ve.getTemplate("template/helloVelocity.vm");

        System.out.println(fillTemplate(t));
    }

    /**
     * 根据绝对路径加载，vm文件置于硬盘某分区中
     */
    private void loadByFilepath() {
        System.out.println("========== loadByFilepath ==========");

        Properties p = new Properties();
        p.put(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "D:\\01_Workspace\\Project\\zp\\spring-notes\\spring-common\\src\\main\\resources");
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);
        Template t = ve.getTemplate("template/helloVelocity.vm");

        System.out.println(fillTemplate(t));
    }

    private void loadByConfig() throws IOException {
        System.out.println("========== loadByConfig ==========");

        Properties p = new Properties();
        p.load(this.getClass().getResourceAsStream("/template/velocity.properties"));
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);
        Template t = ve.getTemplate("template/helloVelocity.vm");

        System.out.println(fillTemplate(t));
    }

    /**
     * 使用文本文件,使用文本文件，如：velocity.properties
     */
    private String fillTemplate(Template t) {
        // 初始化VelocityContext
        VelocityContext ctx = new VelocityContext();
        ctx.put("name", "victor");
        ctx.put("date", (new Date()).toString());
        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        ctx.put("list", temp);

        // 初始化Writer
        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);
        return sw.toString();
    }
}