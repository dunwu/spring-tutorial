package org.zp.notes.spring.common.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.zp.notes.spring.common.file.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpeng0913 on 2016/10/26.
 */
public class JsoupDemo {
    private static List<File> filelist = new ArrayList<File>();

    public static void main(String[] args) throws Exception {
        final String tmplDir = "D:\\01_Workspace\\Project\\xyz\\xyzdev\\apollo\\apollo-underw\\apollo-underw-web\\src\\main\\resources\\velocity\\mailtpl";
//        printAllFilesInDir(new File(tmplDir));

        // 获取所有指定目录下所有的vm文件（包括子目录）
        File[] files = FileUtil.listSpecifiedFiles(new File(tmplDir), "vm");
        for (File f : files) {
            System.out.println(f.getPath());
            Document doc = Jsoup.parse(f, "UTF-8", "");
            Elements links = doc.getElementsByTag("a");
            for (Element link : links) {
                String linkHref = link.attr("href");
                String linkText = link.text();
                System.out.println("linkHref=" + linkHref + ", linkText=" + linkText);
            }
        }
//
//        File input = new File("D:\\00_Temp\\accActive.vm");
//        Document doc = Jsoup.parse(input, "UTF-8", "");
//        Elements links = doc.getElementsByTag("a");
//        for (Element link : links) {
//            String linkHref = link.attr("href");
//            String linkText = link.text();
//            System.out.println("linkHref=" + linkHref + ", linkText=" + linkText);
//        }

    }


}
