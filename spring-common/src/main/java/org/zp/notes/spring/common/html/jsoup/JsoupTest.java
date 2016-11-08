package org.zp.notes.spring.common.html.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int total = 0;
        for (int page = 0; page <= 16; page++) {
            total += printAllTitleInPage(page);
        }
        System.out.println("总文章数：" + total);
    }

    /**
     * 获取指定HTML 文档指定的body
     *
     * @throws IOException
     */
    private static int printAllTitleInPage(int page) throws IOException {
        int count = 0;
        Document doc = Jsoup.connect("http://www.cnblogs.com/jingmoxukong/default.html?page=" + page).get();
        Elements postTitles = doc.body().getElementsByClass("postTitle");
        for (Element postTitle : postTitles) {
            Elements links = postTitle.getElementsByTag("a");
            for (Element link : links) {
                if (link.hasText()){
                    System.out.println(link.text());
                    System.out.println(link.attr("href"));
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 获取博客上的文章标题和链接
     */
    public static void article() {
        Document doc;
        try {
            doc = Jsoup.connect("http://www.cnblogs.com/zyw-205520/").get();
            Elements ListDiv = doc.getElementsByAttributeValue("class", "postTitle");
            for (Element element : ListDiv) {
                Elements links = element.getElementsByTag("a");
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    String linkText = link.text().trim();
                    System.out.println(linkHref);
                    System.out.println(linkText);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 获取指定博客文章的内容
     */
    public static void Blog() {
        Document doc;
        try {
            doc = Jsoup.connect("http://www.cnblogs.com/zyw-205520/archive/2012/12/20/2826402.html").get();
            Elements ListDiv = doc.getElementsByAttributeValue("class", "postBody");
            for (Element element : ListDiv) {
                System.out.println(element.html());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}