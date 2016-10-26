package org.zp.notes.spring.common.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.zp.notes.spring.common.file.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangpeng0913 on 2016/10/26.
 */
public class ActionDemo {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(ActionDemo.class);
    private final String TMPL_DIR =
            "D:\\01_Workspace\\Project\\xyz\\xyzdev\\apollo\\apollo-underw\\apollo-underw-web\\src\\main\\resources\\velocity\\mailtpl";
    private final String TMPL_FILE =
            "D:\\01_Workspace\\Project\\xyz\\xyzdev\\apollo\\apollo-underw\\apollo-underw-web\\src\\main\\resources\\velocity\\mailtpl\\antorderunderwriting.vm";

    public static void main(String[] args) throws Exception {
        ActionDemo action = new ActionDemo();

        action.getAllParams();

        // 打印所有链接
//        for (Object obj : action.getAllLinks().toArray()) {
//            String item = (String)obj;
//            System.out.println(item);
//        }
    }

    public void getAllParams() throws IOException {
        File f = new File(TMPL_FILE);
        Document doc = Jsoup.parse(f, "UTF-8", "");
//        System.out.println(doc.body().outerHtml());
        String content = doc.body().outerHtml();

        Pattern pattern = Pattern.compile("\\$\\{([^\\}]*?)\\}");
        Matcher matcher = pattern.matcher(content);
        System.out.println(matcher.matches());

    }

    public Set<String> getAllLinks() throws IOException {
        Set<String> links = new TreeSet<>();

        // 获取所有指定目录下所有的vm文件（包括子目录）
        File[] files = FileUtil.listSpecifiedFiles(new File(TMPL_DIR), "vm");

        // 获取所有文件的link
        for (File f : files) {
//            log.debug(f.getPath());
            Document doc = Jsoup.parse(f, "UTF-8", "");
            Elements nodes = doc.getElementsByTag("a");
            for (Element node : nodes) {
                String link = node.attr("href");
                if (link.indexOf("&") != -1) {
                    link = link.substring(0, link.indexOf("&"));
                }
                links.add(link);
//                log.debug("link={}", link);
            }
        }

        return links;
    }
}
