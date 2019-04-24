package com.gyportal.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyportal.component.TimeUtil;
import com.gyportal.mapper.AttachmentMapper;
import com.gyportal.model.Attachment;
import com.gyportal.properties.MyProperties;
import com.gyportal.service.AttachmentService;
import com.gyportal.utils.ImageUtil;
import com.gyportal.utils.RequestUtil;
import com.gyportal.utils.ThreadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * create by lihuan at 18/11/30 11:19
 */
@Service(value = "attachmentServiceImpl")
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentMapper attachmentMapper;

    @Resource
    private MyProperties myProperties;

    private TimeUtil timeUtil = new TimeUtil();

    @Override
    public List<Attachment> getAttachmentsByNewsId(Integer newsId) {
        return attachmentMapper.getAttachmentsByNewsId(newsId, RequestUtil.TABLE_NAME_CN);
    }

    @Override
    public void deleteAttachmentByNewsId(Integer newsId, String tableName) {
        List<Attachment> attachments = attachmentMapper.getAttachmentsByNewsId(newsId, tableName);
        if (!attachments.isEmpty()) {
            for (Attachment attachment : attachments) {
                if (attachment.getEffective() == 1) {
                    attachment.setEffective(0);
                    attachmentMapper.save(attachment);
                }
            }
        }
    }

    @Override
    public void batInsertAttachment(JSONObject jsonObject, Integer newsId, String tableName) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("attachments");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject attachmentJson = (JSONObject) jsonArray.get(i);
                Attachment attachment = new Attachment();
                attachment.setNewsId(newsId);
                attachment.setName(attachmentJson.getString("name"));
                attachment.setTableName(tableName);
                attachment.setPath(attachmentJson.getString("url"));
                attachment.setEffective(1);
                attachmentMapper.save(attachment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> asyncUploadAttachment(Map<String, MultipartFile> fileMap, String dir) {
        String path = dir + "/" + timeUtil.getFormatDateForNum();

        //多线程上传,treemap保证图片顺序
        Map<String, String> resultMap = new TreeMap<>();
        for (String filename : fileMap.keySet()) {
            System.out.println("filename: " + filename + "------------");
            MultipartFile file = fileMap.get(filename);

            ThreadUtil.getSortTimeOutThread(() -> {
                String filePath = "";
                if (file != null) {
                    try {
                        String saveFileName = ImageUtil.saveImg(file, myProperties.getPathsProperties().getImage() + path);
                        filePath = path + "/" + saveFileName;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                resultMap.put(filename, filePath);
            });

        }

        while (resultMap.size() != fileMap.size()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return resultMap;
    }



    //----------------------------接口未使用

    @Override
    public void insertAttachment(Attachment attachment) {
        attachmentMapper.save(attachment);
    }

    @Override
    public void updateAttachment(Attachment attachment) {
        attachmentMapper.save(attachment);
    }

}
