package com.gyportal.service.Impl;

import com.gyportal.component.AutoSegmenter;
import com.gyportal.mapper.AttachmentMapper;
import com.gyportal.utils.HTMLSpiritUtil;
import com.gyportal.utils.ImageUtil;
import com.gyportal.utils.StringUtil;
import com.gyportal.model.News;
import com.gyportal.model.PageResult;
import com.gyportal.properties.MyProperties;
import com.gyportal.repository.mybatis.NewsRepository;
import com.gyportal.service.NewsService;
import com.gyportal.component.TimeUtil;
import com.gyportal.utils.ThreadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by lihuan at 18/11/1 11:05
 * 新闻
 */
@Service(value = "newsServiceImpl")
//@CacheConfig(cacheNames = "newsService")
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsRepository newsRepository;

    @Resource
    private AttachmentMapper attachmentMapper;

    @Resource
    private MyProperties myProperties;

    @Resource
    private AutoSegmenter autoSegmenter;

    private TimeUtil timeUtil = new TimeUtil();
    
    private static final String ROOT_DIR = "/u/cms/www/";

    @Override
    public News findNewsById(Integer id, String tableName) {
        increasePV(id, tableName);

        News news = newsRepository.findNewsById(id, tableName);
        if (news != null) {
            setNewsTypeByTableName(news, tableName);
            news.setAttachments(attachmentMapper.getAttachmentsByNewsId(news.getId(), tableName));
        }
        return news;
    }

    @Override
    public Integer findLastInsertId(String tableName) {
        return newsRepository.findLastInsertId(tableName);
    }

    @Override
    public List<News> fetchNearNewsById(Integer id, String type, String tableName) {
        List<News> newsList = new ArrayList<>();
        News frontNews = newsRepository.fetchFrontNewsById(id, type, tableName);
        News behindNews = newsRepository.fetchBehindNewsBYId(id, type, tableName);

        newsList.add(frontNews);
        newsList.add(behindNews);

        setNewsListByTableName(newsList, tableName);

        return newsList;
    }

    @Override
//    @Cacheable(value = "findAll", keyGenerator = "wiselyKeyGenerator")
    public PageResult findAll(int pageSize, int pageNum, String type, String tableName) {

        List<Map<String, Object>> newsList;
        Integer totalElements;
        if ("".equals(type)) {
            newsList = newsRepository.findAll(pageSize, pageSize * (pageNum - 1), tableName);
            totalElements = newsRepository.getNewsCount(tableName);
        } else {
            newsList = newsRepository.findAllByType(pageSize, pageSize * (pageNum - 1), type, tableName);
            totalElements = newsRepository.getCountByType(type, tableName);
        }

        setMapListByTableName(newsList, tableName);

        PageResult pageResult = new PageResult();
        pageResult.setContent(newsList);
        pageResult.setTotalElements(totalElements);
        pageResult.setNumber(pageNum);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages();
        pageResult.setNumberOfElements(newsList.size());

        return pageResult;
    }

    @Override
    public PageResult matchNews(String words, int pageSize, int pageNum, String sort, String type, String tableName) {

        List<News>  newsList;
        Set<String> wordSet = new HashSet<>();
        int totalElements;
        String keyWords = "";

        if(words.length() < 2) {
            if (words.length() > 0) {
                wordSet.add(words);
            }

            keyWords = new StringBuilder("%").append(words).append("%").toString();
            newsList = newsRepository.newsLike(keyWords , pageSize, pageSize * (pageNum - 1), sort, type, tableName);

            totalElements = newsRepository.getNewsLikeCount(keyWords, type, tableName);

        } else {

            //word分词
            wordSet = autoSegmenter.segMore(words);

//            for (String word : wordSet) {
//                keyWords += word + " ";
//            }

            newsList = newsRepository.matchNews(words, pageSize, pageSize * (pageNum - 1), sort, type, tableName);
            totalElements = newsRepository.getMatchNewsCount(words, type, tableName);
        }

        //响应集处理
        for (News news : newsList) {
            String tabloid = autoSegmenter.formatNewsContent(news.getContent(), wordSet);
            news.setTabloid(autoSegmenter.highlightWords(tabloid, wordSet));
            news.setTitle(autoSegmenter.highlightWords(news.getTitle(), wordSet));
            news.setContent("");
        }

        setNewsListByTableName(newsList, tableName);

        PageResult pageResult = new PageResult();
        pageResult.setContent(newsList);
        pageResult.setTotalElements(totalElements);
        pageResult.setNumber(pageNum);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages();
        pageResult.setNumberOfElements(newsList.size());
        return pageResult;
    }

    @Override
    public List<Map<String, Object>> findStaticArticle(String tableName) {
        List<Map<String, Object>> newsList = newsRepository.findStaticArticle(tableName);
        setMapListByTableName(newsList, tableName);
        return newsList;
    }

    @Override
//    @Cacheable(value = "getLatest", keyGenerator = "wiselyKeyGenerator")
    public List<Map<String, Object>> getLatest(int count, String type, String tableName) {

        List<Map<String, Object>> reusltMap;

        if ("".equals(type)) {
            reusltMap =  newsRepository.getLatest(count, tableName);
        } else {
            reusltMap = newsRepository.getLatestByType(count, type, tableName);
        }

        setMapListByTableName(reusltMap, tableName);

        return reusltMap;
    }

    @Override
    public void insertNews(News news, String tableName) {
        //测试
//        if (news.getPublish_date() != null) {
//            news.setPublish_date(news.getPublish_date());
//            news.setUpdate_date(news.getPublish_date());
//        }

        //切割首张图片
        String content = news.getContent();
        int imageFirstIndex = content.indexOf("<img src=\"");
        if (imageFirstIndex >= 0) {
            int imageLastIndex = content.indexOf("\">", imageFirstIndex);
            news.setImage(content.substring(imageFirstIndex + 10, imageLastIndex));
        }

        news.setPage_view(0);
        news.setPublish_date(timeUtil.getFormatDateForSix());
        news.setUpdate_date(timeUtil.getFormatDateForSix());
        news.setEffective(1);
        news.setStick(0);

        newsRepository.insertNews(tableName, news);
    }

    @Override
    public void insertNewsList(List<News> newsList, String tableName) {
        for (News news : newsList) {

            newsRepository.insertNews(tableName, news);
        }
    }

    @Override
    public void updateNews(News news, String tableName) {

        //切割首张图片
        String content = news.getContent();
        int imageFirstIndex = content.indexOf("<img src=\"");
        if (imageFirstIndex >= 0) {
            int imageLastIndex = content.indexOf("\">", imageFirstIndex);
            news.setImage(content.substring(imageFirstIndex + 10, imageLastIndex));
        }

        news.setUpdate_date(timeUtil.getFormatDateForSix());

        newsRepository.updateNews(tableName, news);
    }

    @Override
    public void deleteNews(List<String> groupId, String username, String tableName) {

        newsRepository.deleteNews(groupId, username, timeUtil.getFormatDateForSix(), tableName);

    }

    @Override
    public Integer getNewsCount(String tableName) {
        return newsRepository.getNewsCount(tableName);
    }

    @Override
    public Integer getNewsCountByType(String type, String tableName) {
        return newsRepository.getCountByType(type, tableName);
    }

    @Override
    public String savePicture(MultipartFile image, String dir) {

        String path = ROOT_DIR;

        //判断前置路径
        if (StringUtils.isBlank(dir)) {
            path += timeUtil.getFormatDateForNum(); //新闻图片资源
        } else {
            path += dir;  //其他图片资源
        }

        String pictureUrl = null;
        try {
            if (image != null) {
                String fileName = ImageUtil.saveImg(image, myProperties.getPathsProperties().getImage() + path);
                pictureUrl = path + "/" + fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pictureUrl;
    }

    @Override
    public String savePictureByBase64(String imgStr, String dir) {
        String path = dir + "/" + timeUtil.getFormatDateForNum();

        String pictureUrl = null;
        try {
            if (!StringUtil.isEmpty(imgStr)) {
                String fileName = ImageUtil.Base64ToImage(imgStr, myProperties.getPathsProperties().getImage() + path);
                pictureUrl = path + "/" + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pictureUrl;
    }

    @Override
    public Map<Integer, String> asyncSavePictureByBase64(Map<Integer, String> map, String dir) {
        String path = dir + "/" + timeUtil.getFormatDateForNum();

        //多线程上传,treemap保证图片顺序
        Map<Integer, String> resultMap = new TreeMap<>();
        for (Integer key : map.keySet()) {
            String imgStr = map.get(key);

            ThreadUtil.getSortTimeOutThread(() -> {
                String pictureUrl = "";
                if (!StringUtil.isEmpty(imgStr)) {
                    String fileName = ImageUtil.Base64ToImage(imgStr, myProperties.getPathsProperties().getImage() + path);
                    pictureUrl = path + "/" + fileName;
                    }

                resultMap.put(key, pictureUrl);
            });

        }
        return resultMap;
    }

    @Override
    public void increasePV(int id, String tableName) {
        News news = newsRepository.findNewsById(id, tableName);
        if (news != null) {
            news.setPage_view(news.getPage_view() + 1);
            newsRepository.updateNews(tableName, news);
        }
    }

    @Override
    public News getHotNews(String tableName) {
        News news = newsRepository.getHotNews(tableName);
        if (news != null) {
            setNewsTypeByTableName(news, tableName);
            news.setContent("");
        }

        return news;
    }

    @Override
    public void setHotNews(int id, String tableName) {

        newsRepository.clearHotNews(tableName);
        newsRepository.setHotNews(id, tableName);
    }

    @Override
    public List<News> getAllNews(String tableName) {
        List<News> newsList = newsRepository.getAllNews(tableName);
        setNewsListByTableName(newsList, tableName);
        return newsList;
    }

    /**
     * 维护用
     * @param news
     */
    @Override
    public void updateNewsTime(News news, String tableName) {
        newsRepository.updateNews(tableName, news);
    }

    @Override
    public List<Map<String, Object>> getBanners(String tableName) {
        List<Map<String, Object>> newsList = newsRepository.getBanners(tableName);
        setMapListByTableName(newsList, tableName);
        return newsList;
    }


    @Override
    public String spiritTabloid(String content) {
        content = HTMLSpiritUtil.delHTMLTag(HTMLSpiritUtil.stripHtml(content));
        int lastIndex = 150 >= content.length() ? content.length() : 150;
        String tabloid = content.substring(0, lastIndex);
        tabloid = HTMLSpiritUtil.replace(tabloid);
        if (tabloid == null) {
            tabloid = "";
        }
        return tabloid;
    }


    public void setNewsTypeByTableName(News news, String tableName) {

        if (tableName.equals("news_en") && news != null) {
            news.setType(News.getNewsEnType(news.getType()));
        }

    }

    public void setNewsListByTableName(List<News> newsList, String tableName) {

        if (tableName.equals("news_en")) {
            for (News news : newsList){
                news.setType(News.getNewsEnType(news.getType()));
            }
        }
    }

    public void setMapListByTableName(List<Map<String, Object>> mapList, String tableName) {
        if (tableName.equals("news_en")) {
            for (Map<String, Object> map : mapList) {
                map.replace("type", News.getNewsEnType((String) map.get("type")));
            }
        }
    }

}
