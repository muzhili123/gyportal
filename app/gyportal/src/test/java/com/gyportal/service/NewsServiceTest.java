package com.gyportal.service;

import com.gyportal.model.PageResult;
import com.gyportal.utils.HTMLSpiritUtil;
import com.gyportal.model.News;
import com.gyportal.component.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.*;
import java.util.*;

/**
 * create by lihuan at 18/11/2 22:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {

    @Resource
    private NewsService newsService;

    private TimeUtil timeUtil = new TimeUtil();

//    @Test
//    public void insertNews() {
//        News news = new News();
//        news.setIssuer("mihua");
//        news.setType("通知公告EX");
//        news.setTitle("关于召开河南省科技惠民计划专项示范点建设工作推进会的通知");
//
////        newsService.insertNews(news);
//    }
//
//    @Test
//    public void insertNewsBat() throws IOException {
//
//        File sqlfile = new File("/Users/daocloud/quanxi/t_ycyl_article_detail.sql");
//        System.out.println(sqlfile.getName() + "-------------------");
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(sqlfile));
//
//        List<News> newsList = new ArrayList<>();
//        String line;
//        while ((line = bufferedReader.readLine()).length() > 0) {
//            System.out.println(line);
//            String string = line;
//            //查找指定字符第一次出现的位置
//            int contentFirst = string.indexOf("<");
//            int contentLast = string.lastIndexOf(">");
//
//            int urlFirst = string.indexOf("http");
//            int urlLast = string.lastIndexOf("jhtml");
//
//            int titleFirst = string.indexOf("\\n', '");
//            int titleLast = string.lastIndexOf("'");
//
//            int dateFirst = string.indexOf("本站编辑   ");
//            int dateLast = string.lastIndexOf("   浏览次数：\\n");
//
//            int typeFirst = string.indexOf("jhtml', '");
//            int typeLast = string.indexOf("', '<div");
//
//            int imageFirst = string.indexOf("<img alt=\"\" src=\"");
//            int imageLast = string.indexOf(".jpg");
//
//            News news = new News();
//            news.setIssuer("mihua");
////            news.setType("通知公告");
//            news.setType(string.substring(typeFirst + 9, typeLast));
//            news.setContent(string.substring(contentFirst, contentLast + 1));
//            news.setUrl("--");
//            news.setTitle(string.substring(titleFirst + 6, titleLast));
//            news.setPublish_date(string.substring(dateFirst + 7, dateLast));
//
//            if (imageFirst > 0 && imageLast >0 && (imageLast - imageFirst) < 255) {
//                news.setImage(string.substring(imageFirst + 17, imageLast + 4));
//                System.out.println(news.getImage());
//            }
//
//            newsList.add(news);
//
////            newsService.insertNews(news);
//
////            if (newsList.size() > 100) {
//////                newsService.insertNewsList(newsList);
////                return;
////            }
//
//        }
//        bufferedReader.close();
//
//    }
//
//    @Test
//    public void updateNews() {
//        News news = new News();
//        news.setId(22);
//        news.setIssuer("mihua");
//        news.setTitle("新文");
//        news.setContent("content");
//        news.setType("1");
//        news.setPublish_date(timeUtil.getFormatDateForSix());
////        newsService.updateNews(news);
//    }
//
//    @Test
//    public void updateImage() {
////        News news = newsService.findNewsById(1174);
////        newsService.updateNews(news);
//    }
//
//    @Test
//    public void setTabloidNotNull() {
////        List<News> newsList = newsService.getAllNews();
//        for (News news : newsList) {
//            if (news.getTabloid() == null) {
//                news.setTabloid("");
//            }
////            newsService.updateNews(news);
//        }
//    }
//
//    @Test
//    public void updateTabloid() {
//        String type = "图片新闻";
//        List<News> newsList = newsService.getAllNews();
//        for (News news : newsList) {
//            String content = HTMLSpiritUtil.delHTMLTag(HTMLSpiritUtil.stripHtml(news.getContent()));
//            int lastIndex = 150 >= content.length() ? content.length() : 150;
//            String tabloid = content.substring(0, lastIndex);
//            if (tabloid == null) {
//                tabloid = "";
//            }
//            news.setTabloid(tabloid);
//            newsService.updateNews(news);
//        }
//    }
//
//    @Test
//    public void matchNews() {
//        List<String> words = new ArrayList<>();
//        words.add("国家");
////        words.add("医疗");
//
////        PageResult pageResult = newsService.matchNews(words, 3, 1);
////        List<News> newsList = (List<News>) pageResult.getContent();
////        for (News news : newsList) {
////            System.out.println(news.getTitle());
////        }
//
//    }
//
////    @Test
////    public void spiritTabloid() {
////        String type = "行业资讯";
////        List<News> newsList = newsService.getLatest(newsService.getNewsCountByType(type), type);
////        for (News news : newsList) {
////            if (news.getTabloid() == null) {
////                continue;
////            }
////            String tabloid = HTMLSpiritUtil.replace(news.getTabloid());
////            news.setTabloid(tabloid);
////            newsService.updateNews(news);
////        }
////    }
////
////    @Test
////    public void updateUpdateTime() {
////        String type = "图片新闻";
////        List<News> newsList = newsService.getLatest(newsService.getNewsCount(), type);
////        for (News news : newsList) {
////            news.setUpdate_date(news.getPublish_date());
////            newsService.updateNews(news);
////        }
////    }
////
////    @Test
////    public void updateContent() {
////        String type = "行业资讯";
////        List<News> newsList = newsService.getLatest(newsService.getNewsCountByType(type), type);
////        for (News news : newsList) {
////            String content = HTMLSpiritUtil.replace(news.getContent());
////            news.setContent(content);
////            newsService.updateNews(news);
////        }
////    }
//
////    @Test
////    public void deleteNews() {
////        List<String> list = Arrays.asList("20", "21");
////        newsService.deleteNews(list);
////    }
}