/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package org.zp.notes.spring.common.html.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * @author Zhang Peng
 * @date 2016/11/8.
 */
public class JsoupDemo {
    public static void main(String[] args) throws IOException {
        // 从一个html字符串加载Document对象
        final String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document docFromStr = Jsoup.parse(html);
        System.out.println("doc内容：\n" + docFromStr.toString());

        // 从一个URL加载Document对象
        Document docFromUrl = Jsoup.connect("https://www.baidu.com/").get();
        System.out.println("www.baidu.com内容：\n" + docFromUrl.toString());

        // 从一个文件加载Document对象
        final String projPath = System.getProperty("user.dir");
        final String filePath = projPath + "\\spring-common\\src\\main\\resources\\html\\example.html";
        File input = new File(filePath);
        Document docFromFile = Jsoup.parse(input, "UTF-8", "");
        System.out.println("example.html内容：\n" + docFromFile.toString());

        // 解析body
        Element body = docFromStr.body();
        System.out.println("body内容：\n" + body.toString());

        // 遍历一个Document对象
        Element content = docFromUrl.body();
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            System.out.println("linkHref: " + linkHref);
            System.out.println("linkText: " + linkText);
        }

        // 使用选择器语法来查找元素
        //带有href属性的a元素
        Elements hrefs = docFromUrl.select("a[href]");
        System.out.println("hrefs: " + hrefs.toString());
        //扩展名为.png的图片
        Elements pngs = docFromUrl.select("img[src$=.png]");
        System.out.println("pngs: " + pngs.toString());
        //class等于masthead的div标签
        Element head_wrappers = docFromUrl.select("div.head_wrapper").first();
        System.out.println("head_wrapper: " + head_wrappers.toString());
        //在h3元素之后的a元素
        Elements resultLinks = docFromUrl.select("div.head_wrapper > a");
        System.out.println("resultLinks: " + resultLinks.toString());

        // 从元素集合抽取属性、文本和html内容
        Element link = docFromUrl.select("a").first();//查找第一个a元素
        System.out.println("text: " + docFromUrl.body().text()); //取得字符串中的文本
        System.out.println("linkHref: " + link.attr("href")); //取得字符串中的文本
        System.out.println("linkText: " + link.text()); //取得链接地址中的文本
        System.out.println("linkOuterH: " + link.outerHtml());
        System.out.println("linkInnerH: " + link.html()); //取得链接内的html内容

        // URL处理
        System.out.println("relHref: " + link.attr("href")); //取得字符串中的文本
        System.out.println("absHref: " + link.attr("abs:href")); //取得字符串中的文本

        // 设置属性的值

    }
}
