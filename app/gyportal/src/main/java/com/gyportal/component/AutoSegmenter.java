package com.gyportal.component;

import com.gyportal.utils.HTMLSpiritUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * create by lihuan at 18/12/19 09:59
 */
@Component
public class AutoSegmenter {

    /**
     * 获取文本的所有分词结果
     * @param text 文本
     * @return 所有的分词结果，去除重复
     */
    public Set<String> segMore(String text) {
        Map<String, String> map = new HashMap<>();
        for(SegmentationAlgorithm segmentationAlgorithm : SegmentationAlgorithm.values()){
            map.put(segmentationAlgorithm.getDes(), seg(text, segmentationAlgorithm));
        }
        Set<String> words = map.values().stream().collect(Collectors.toSet());
        words.add(text);
        return words;
    }

    private String seg(String text, SegmentationAlgorithm segmentationAlgorithm) {
        StringBuilder result = new StringBuilder();
        for(Word word : WordSegmenter.segWithStopWords(text, segmentationAlgorithm)){
            result.append(word.getText()).append(" ");
        }
        return result.toString();
    }

    /**
     * 新闻文本处理
     * @param text 新闻内容
     * @return 简介
     */
    public String formatNewsContent(String text, Set<String> words) {
        //格式化
        String content = HTMLSpiritUtil.delHTMLTag(HTMLSpiritUtil.stripHtml(text));
        content = HTMLSpiritUtil.replace(content);

        //辞集合并
        words = megreWordSet(words);

        //起始索引
        int firstIndex = getFirstIndex(words, content);
        int subLength = 200;

        int lastIndex = subLength >= (content.length() - firstIndex)
                ? (content.length() - firstIndex) : (firstIndex + subLength);

        if (firstIndex > lastIndex) {
            return content.substring(0, lastIndex);
        }
        String tabloid = content.substring(firstIndex, lastIndex);

        return tabloid;
    }

    private int getFirstIndex(Set<String> words, String content) {
        //起始索引
        int firstIndex = 0;
        //句号距离起始索引长度最大值
        int pointToWordLength = 50;

        for (String word : words) {
            //优先匹配词组
            if (word.length() >= 2) {

                int index = content.indexOf(word);
                firstIndex = firstIndex >= index ? firstIndex : index;

            }


        }

        if (firstIndex == 0) {
            return firstIndex;
        }

        //优先句号索引
        int pointIndex = content.lastIndexOf("。", firstIndex);

        if (pointIndex > 0 && Math.abs(firstIndex - pointIndex) <= pointToWordLength) {
            firstIndex = pointIndex + 1;
        } else {
            //逗号索引
            int commaIndex = content.lastIndexOf("，", firstIndex);
            if (commaIndex > 0 && Math.abs(firstIndex - commaIndex) <= pointToWordLength) {
                firstIndex = commaIndex + 1;
            }

        }

        return firstIndex;

    }

    /**
     * 高亮关键字
     * @param text
     * @param words
     * @return
     */
    public String highlightWords(String text, Set<String> words) {

        words = megreWordSet(words);

        StringBuffer stringBuffer = new StringBuffer();

        for (String word : words) {

            int firstIndex = 0;
            int wordIndex = text.indexOf(word);

            while (wordIndex >= 0) {
                stringBuffer.append(text, 0, wordIndex);
                stringBuffer.append("<span style='color:red'>" + word + "</span>");
                firstIndex = stringBuffer.length();
                stringBuffer.append(text, wordIndex + word.length(), text.length());

                text = stringBuffer.toString().trim();
                stringBuffer.setLength(0);

                wordIndex = text.indexOf(word, firstIndex);
            }

        }
        return text;
    }

    /**
     * 合并关键字集
     * @param words
     * @return
     */
    private Set<String> megreWordSet(Set<String> words) {
        Set<String> megreSet = new HashSet<>();
        for (String word : words) {
            CollectionUtils.addAll(megreSet, word.split(" "));
        }

        return megreSet;
    }

}
